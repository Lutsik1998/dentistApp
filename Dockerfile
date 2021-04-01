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
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/", "-jar", "./dentist-api.jar"]