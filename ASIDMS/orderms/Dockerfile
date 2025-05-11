# Usa uma imagem oficial do Java 17 como base
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no container
WORKDIR /app

# Copia os ficheiros do projeto Maven
COPY . .

# Dá permissões de execução ao wrapper do Maven
RUN chmod +x mvnw

# Compila o projeto sem correr testes
RUN ./mvnw clean package -DskipTests

# Expõe a porta 8080 (porta usada pela app Spring Boot)
EXPOSE 8080

# Comando para correr o JAR gerado dinamicamente
CMD ["sh", "-c", "java -jar target/*.jar"]
