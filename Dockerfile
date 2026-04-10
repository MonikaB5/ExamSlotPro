# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY mavenProject/pom.xml .
RUN mvn dependency:resolve
COPY mavenProject/src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/mavenProject.jar app.jar
EXPOSE 7070
ENV PORT=7070
CMD ["java", "-jar", "app.jar"]
