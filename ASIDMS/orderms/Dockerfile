# Usa imagem com Java 17
FROM openjdk:17-jdk

# Diretório de trabalho
WORKDIR /app

# Copiar tudo
COPY . .

# Permitir execução do wrapper Maven
RUN chmod +x mvnw

# Compilar sem testes
RUN ./mvnw clean package -DskipTests

# Expor a porta
EXPOSE 8080

# Executar com wildcard funcional
CMD sh -c "java -jar target/*.jar"
