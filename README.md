# 🍷 Wine Quality Prediction - Apache Spark on AWS

This project demonstrates a parallel machine learning workflow using Apache Spark and Docker on AWS. The objective is to train and deploy a wine quality prediction model using logistic regression. The solution includes parallel model training on a Spark cluster and a prediction application running inside a Docker container.

---

## ✅ Project Components

| Component | Description |
|----------|-------------|
| Language | Java (Maven project) |
| ML Model | Logistic Regression (Spark MLlib) |
| Platform | Apache Spark 3.3.3, Hadoop 3.3.1 |
| Deployment | AWS EMR (Training), Docker (Prediction) |

---

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

## 🚀 How to Run (Prediction Phase)

### Option 1: Run Locally in Docker (Single EC2)
```bash
sudo docker run --rm wine-predictor:local
```

### Option 2: Run on EMR (YARN mode)
*(Optional, if desired to scale Docker to YARN)*

---

## 🧱 Build Docker Image (If Needed)
```bash
docker build -t wine-predictor:local .
```

---

## 📁 DockerHub & GitHub

- 🔗 **Docker Image**: https://hub.docker.com/r/vishalk722/wine-ml-app
- 🔗 **GitHub Repo**: https://github.com/vishal2609/CC-CS643-Prgm-Assgn-2-

---

## 📸 Screenshots

See [`screenshots/`](./screenshots) folder for:
- Parallel model training on EMR
- Prediction result with F1 score printed

---


## 📦 Submission Summary

- ✔ GitHub repo includes training and prediction code.
- ✔ Docker Hub image ready for prediction.
- ✔ Final run screenshot included in `screenshots/`.
- ✔ README includes clear build/run steps.

---

