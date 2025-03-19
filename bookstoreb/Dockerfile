# Usa uma imagem do OpenJDK 17
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia TODOS os ficheiros dentro de `bookstoreb` para dentro do container
COPY . /app

# Dá permissão ao script `mvnw`
RUN chmod +x mvnw

# Compila o projeto com Maven
RUN ./mvnw clean package -DskipTests

# Expõe a porta 8080 para acesso externo
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/*.jar"]