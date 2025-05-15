FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
COPY wait-for-it.sh .
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["sh", "./wait-for-it.sh", "orderms:3306", "java", "-jar", "app.jar"]
