# Usa imagem base com Java 17
FROM openjdk:17-jdk-slim

# Diretório de trabalho
WORKDIR /app

# Copia apenas o JAR já construído (assumindo que já o construíste com Maven fora do Docker)
COPY target/*.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Executar o serviço
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
