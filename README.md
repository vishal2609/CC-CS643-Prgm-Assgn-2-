# Wine Quality Prediction with Spark on AWS

## Overview
This project implements a wine quality prediction ML model using Apache Spark on AWS EMR. The model is trained in parallel on 4 EC2 instances and deployed for prediction on a single instance, both with and without Docker.

### Assignment Requirements
- **Training**: Train a model on `TrainingDataset.csv` using 4 EC2 instances.
- **Validation**: Evaluate and tune the model using `ValidationDataset.csv`.
- **Prediction**: Perform predictions on a single EC2 instance with and without docker, outputting the F1 score.
- **Docker**: Containerize the prediction app for easy deployment.
- **Implementation**: Java, Ubuntu Linux, Spark MLlib.

## 🔧 Project Structure

```
.
├── src/                            # Java source code for training and prediction
│   └── main/java/com/wine/        # WinePrediction.java, WinePredictApp.java
├── target/                        # (Generated) Final JAR after build (excluded from GitHub)
├── Dockerfile                     # Final working Dockerfile (local mode)
├── pom.xml                        # Maven configuration file
├── README.md                      # Project documentation
└── CS643-VK722-Instruction-Document.pdf  # Step by Step Instruction document
```

---

## 📊 Model Details

- **Training**: Runs on 4-node EMR cluster using Spark.
- **Validation**: Evaluated using F1 Score on ValidationDataset.csv.
- **Final Output**: F1 Score printed in logs.


---

## 📁 DockerHub & GitHub

- 🔗 **Docker Image**: https://hub.docker.com/r/vishalk722/wine-ml-app/tags
- 🔗 **GitHub Repo**: https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-
- 🔗 **Instruction Doc**: https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-/blob/main/CS643-VK722-Instruction-Document.pdf

---

## Setup and Execution
- See `CS643-VK722-Instruction-Document.pdf` for detailed step-by-step instructions please follow below file. 
- **Note : Git Hub Repo Read Me file is just overview, for detailed instruction document follow below link
- Link: https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-/blob/main/CS643-VK722-Instruction-Document.pdf

### 1. Clone the Repository
```bash
git clone https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-.git
cd CC-CS643-Prgm-Assgn-2-
```

### 2. Maven Application packaging 
- Build locally: `mvn clean package`

### 3. uploaded File on S3 & Create EMR cluster 
- Details are mentioned in Instruction Document

### 4. Train model in parallel (4 nodes)
    ```bash
        spark-submit \
        --class com.wine.WinePrediction \
        --master yarn \
        --deploy-mode cluster \
        --num-executors 4 \
        --executor-cores 2 \
        --executor-memory 2G \
        target/wine-ml-spark-1.0-SNAPSHOT.jar
    ```
### 5. Prediction on single cluster/EMR
- **Without Docker**:
  ```bash
    spark-submit \
    --class com.wine.WinePredictApp \
    --master yarn \
    target/wine-ml-spark-1.0-SNAPSHOT.jar

  ```
- **With Docker**:
  ```bash
  docker pull vishalk722/wine-ml-app:v16
  sudo docker run --rm   -v $HOME/.ivy2:/root/.ivy2   -v $HOME:/root   -e HOME=/root   -e SPARK_SUBMIT_OPTS="-Divy.cache.dir=/root/.ivy2/cache -Divy.home=/root/.ivy2"   --user root   vishalk722/wine-ml-app:v16
  ```

## Results
- F1 Score on Validation-Data, Model used Logistic Regression: ~60%.

## Challenges and Solutions
- Java path issues: Fixed by setting `JAVA_HOME=/usr/local/openjdk-8`.
- S3 scheme mismatch: Used `s3a://` instead of `s3://`.
- Hadoop tarball download issues: Pre-downloaded the file from `archive.apache.org`.