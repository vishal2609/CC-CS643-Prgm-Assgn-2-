# Base Image
FROM openjdk:8-jdk

# Set environment variables
ENV HADOOP_VERSION=3.3.1
ENV SPARK_VERSION=3.3.3
ENV JAVA_HOME=/usr/local/openjdk-8
ENV SPARK_HOME=/opt/spark
ENV PATH=$SPARK_HOME/bin:$PATH

# Install dependencies and clean up
RUN apt-get update && \
    apt-get install -y curl tar unzip && \
    rm -rf /var/lib/apt/lists/*

# Install Spark from the archive URL
RUN curl -LO https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop3.tgz && \
    tar -xzf spark-${SPARK_VERSION}-bin-hadoop3.tgz -C /opt && \
    ln -s /opt/spark-${SPARK_VERSION}-bin-hadoop3 /opt/spark && \
    rm spark-${SPARK_VERSION}-bin-hadoop3.tgz

# Add Hadoop AWS dependencies for S3 access (use 3.3.1 to match Spark's Hadoop version)
RUN curl -L https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/${HADOOP_VERSION}/hadoop-aws-${HADOOP_VERSION}.jar -o /opt/spark/jars/hadoop-aws-${HADOOP_VERSION}.jar && \
    curl -L https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.12.262/aws-java-sdk-bundle-1.12.262.jar -o /opt/spark/jars/aws-java-sdk-bundle-1.12.262.jar

# Set working directory
WORKDIR /app

# Copy application jar
COPY target/wine-ml-spark-1.0-SNAPSHOT.jar /app/app.jar

# Add Ivy cache directory (optional)
RUN mkdir -p /root/.ivy2

# Set environment variables for Ivy (optional)
ENV HOME=/root
ENV SPARK_SUBMIT_OPTS="-Divy.cache.dir=/root/.ivy2/cache -Divy.home=/root/.ivy2"

# Run Spark application in local mode with S3 support
ENTRYPOINT ["/opt/spark/bin/spark-submit", \
  "--class", "com.wine.WinePredictApp", \
  "--master", "local[*]", \
  "--conf", "spark.hadoop.fs.s3a.impl=org.apache.hadoop.fs.s3a.S3AFileSystem", \
  "/app/app.jar"]