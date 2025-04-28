package com.wine;

import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class WinePredictApp {

    public static void main(String[] args) {

        // 1. Initialize Spark Session
        SparkSession spark = SparkSession.builder()
                .appName("Wine Quality Prediction - Prediction Phase")
                .getOrCreate();

        // 2. Load Validation dataset
        Dataset<Row> data = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ";")
                .csv("s3://wine-ml-bucket-vk722/ValidationDataset.csv");

        // 3. Clean column names (remove extra quotes)
        for (String colName : data.columns()) {
            String cleanName = colName.replace("\"", "");
            data = data.withColumnRenamed(colName, cleanName);
        }

        // 4. Assemble features
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[] {
                        "fixed acidity", "volatile acidity", "citric acid",
                        "residual sugar", "chlorides", "free sulfur dioxide",
                        "total sulfur dioxide", "density", "pH", "sulphates", "alcohol"
                })
                .setOutputCol("features");

        Dataset<Row> output = assembler.transform(data);

        // 5. Prepare test data
        Dataset<Row> testData = output.withColumnRenamed("quality", "label")
                .select("features", "label");

        // 6. Load the trained model
        LogisticRegressionModel model = LogisticRegressionModel.load("s3://wine-ml-bucket-vk722/wine_quality_model");

        // 7. Perform prediction
        Dataset<Row> predictions = model.transform(testData);

        // 8. Evaluate model
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol("label")
                .setPredictionCol("prediction")
                .setMetricName("f1");

        double f1Score = evaluator.evaluate(predictions);
        System.out.println("F1 Score on Validation Data = " + f1Score);

        // 9. Stop Spark
        spark.stop();
    }
}
