
# Wine Quality Prediction - Cloud Computing Project

## Overview
This project focuses on building a **Wine Quality Prediction** machine learning model using **Apache Spark** on **AWS EMR** with **4 EC2 instances** for training and **1 EC2 instance** for prediction. It was implemented in **Java** and integrated with **S3 storage**.

---

## Project Phases Completed

- ✅ Created EMR Cluster (4 nodes: 1 master + 3 core nodes).
- ✅ Launched cluster with Hadoop and Spark applications.
- ✅ Connected via SSH (PuTTY on Windows).
- ✅ Uploaded `TrainingDataset.csv` and `ValidationDataset.csv` to S3 bucket.
- ✅ Set up Java project with Maven.
- ✅ Built training application: `WinePrediction.java`.
- ✅ Built prediction application: `WinePredictApp.java`.
- ✅ Trained Logistic Regression model with MLlib.
- ✅ Saved and loaded model from S3.
- ✅ Performed mini model tests.
- ✅ Evaluated model performance using F1 Score.
- ✅ Successfully ran applications using `spark-submit` on EMR.

---

## Files

- `WinePrediction.java` → Training code
- `WinePredictApp.java` → Prediction code
- `pom.xml` → Maven dependencies and build file
- `wine-ml-spark-1.0-SNAPSHOT.jar` → Built Java project for Spark
- Datasets in S3 bucket: `TrainingDataset.csv`, `ValidationDataset.csv`

---

## Important Details

- **Programming Language**: Java
- **Framework**: Apache Spark MLlib
- **Cloud Platform**: AWS (EMR, S3)
- **Model Used**: Logistic Regression
- **Performance Metric**: F1 Score
- **Data Format**: CSV (with cleaned column headers)

---

## Notes
- For screenshots of YARN UI and Spark jobs, use Windows Snipping Tool or built-in screenshot shortcuts.
- All logs were verified using Spark History Server and YARN Resource Manager.

---

## Next Steps
- Dockerize the Wine Prediction App.
- Test containerized app on a single EC2 instance.

---

# 📦 Good luck and great job so far!
