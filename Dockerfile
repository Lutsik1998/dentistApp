# Build stage
FROM maven:3.6.3-openjdk-11-slim AS mavenbuild
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn -f ./pom.xml clean package

# Package stage
FROM openjdk:11-jre-slim
COPY --from=mavenbuild target/*.jar ./
EXPOSE 8080
RUN bash -c 'touch scrum-test-docker.jar'
<<<<<<< HEAD
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "./dentist-api.jar"]
=======
ENTRYPOINT ["java", "-jar", "./dentist-api.jar"]
>>>>>>> e05f116786578145697ff114bb3b5182d00503f4
