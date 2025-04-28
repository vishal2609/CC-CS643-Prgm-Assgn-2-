package com.wine;

import java.io.IOException;

import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class WinePrediction {

    public static void main(String[] args) throws IOException {

        // 1. Initialize Spark Session
        SparkSession spark = SparkSession.builder()
                .appName("Wine Quality Prediction")
                .getOrCreate();

        // 2. Read CSV file from S3 bucket
        Dataset<Row> data = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ";")
                .csv("s3://wine-ml-bucket-vk722/TrainingDataset.csv");

        // 3. Clean column names to remove extra quotes
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

        // 5. Prepare training data
        Dataset<Row> trainingData = output.withColumnRenamed("quality", "label");

        // 6. Train Logistic Regression model
        LogisticRegression lr = new LogisticRegression();
        LogisticRegressionModel model = lr.fit(trainingData);

        // 7. Save Trained Model to S3
        model.write().overwrite().save("s3://wine-ml-bucket-vk722/wine_quality_model");

        // 8. Evaluate Model on Training Data
        Dataset<Row> predictions = model.transform(trainingData);
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
                .setMetricName("f1");

        double f1Score = evaluator.evaluate(predictions);
        System.out.println("F1 Score on training data = " + f1Score);

        // 9. 🚀 MINI TEST: Load model and predict again (quick test)
        System.out.println("Loading saved model for mini test...");
        LogisticRegressionModel loadedModel = LogisticRegressionModel.load("s3://wine-ml-bucket-vk722/wine_quality_model");

        Dataset<Row> newPredictions = loadedModel.transform(trainingData);

        System.out.println("Showing 10 predictions from loaded model:");
        newPredictions.select("features", "label", "prediction").show(10);

        // 10. Stop Spark
        spark.stop();
    }
}
