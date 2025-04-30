# Wine Quality Prediction with Spark on AWS

## Overview
This project implements a wine quality prediction ML model using Apache Spark on AWS EMR. The model is trained in parallel on 4 EC2 instances and deployed for prediction on a single instance, both with and without Docker.

### Assignment Requirements
- **Training**: Train a model on `TrainingDataset.csv` using 4 EC2 instances.
- **Validation**: Evaluate and tune the model using `ValidationDataset.csv`.
- **Prediction**: Perform predictions on a single EC2 instance, outputting the F1 score.
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
└── screenshots/                   # Screenshots of final EMR run and Docker results
```

---

## 📊 Model Details

- **Training**: Runs on 4-node EMR cluster using Spark.
- **Validation**: Evaluated using F1 Score on ValidationDataset.csv.
- **Final Output**: F1 Score printed in logs.

---
## 🐳 Docker Image

- **Built on Base**: `openjdk:8-jdk`
- **Includes**: Apache Spark 3.3.3, Hadoop 3.3.1, AWS S3 support (hadoop-aws + aws-sdk-bundle)
- **Run Mode**: `local[*]`

---

---

## 📁 DockerHub & GitHub

- 🔗 **Docker Image**: https://hub.docker.com/r/vishalk722/wine-ml-app
- 🔗 **GitHub Repo**: https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-

---

## Setup and Execution
See `submission_instructions.docx` for detailed step-by-step instructions. Summary:

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/wine-ml-spark.git
cd wine-ml-spark
```

### 2. Model Training
- Build: `mvn clean package`
- Run on EMR with YARN:
  ```bash
  spark-submit --class com.wine.WineTrainApp --master yarn --deploy-mode client target/wine-ml-spark-1.0-SNAPSHOT.jar
  ```

### 3. Prediction
- **Without Docker**:
  ```bash
  spark-submit --class com.wine.WinePredictApp --master local[*] target/wine-ml-spark-1.0-SNAPSHOT.jar
  ```
- **With Docker**:
  ```bash
  docker pull <your-dockerhub-username>/wine-predictor:v16
  sudo docker run --rm -v $HOME/.ivy2:/root/.ivy2 -v $HOME:/root -e HOME=/root -e SPARK_SUBMIT_OPTS="-Divy.cache.dir=/root/.ivy2/cache -Divy.home=/root/.ivy2" --user root <your-dockerhub-username>/wine-predictor:v16
  ```

## Results
- Model: Random Forest (100 trees, max depth 10).
- F1 Score on Validation Data: [Insert your F1 score here].

## Challenges and Solutions
- Java path issues: Fixed by setting `JAVA_HOME=/usr/local/openjdk-8`.
- S3 scheme mismatch: Used `s3a://` instead of `s3://`.
- Hadoop tarball download issues: Pre-downloaded the file from `archive.apache.org`.