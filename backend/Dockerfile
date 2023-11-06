FROM openjdk:11-jdk-slim
COPY ./target/*.jar blog-app-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","blog-app-0.0.1-SNAPSHOT.jar"]
