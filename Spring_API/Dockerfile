# Build stage
FROM maven:3.6.3-openjdk-11-slim AS mavenbuild
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn -f ./pom.xml clean package -DskipTests

# Package stage
FROM openjdk:11-jre-slim
COPY --from=mavenbuild target/*.jar /
EXPOSE 8080
RUN mkdir -p /img_db
#RUN bash -c 'touch scrum-test-docker.jar'
ENTRYPOINT ["java", "-jar", "-Dspring.data.mongodb.host=main-db", "-Dspring.redis.host=cache-db", "/dentist-api.jar"]
