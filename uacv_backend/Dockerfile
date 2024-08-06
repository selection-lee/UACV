FROM eclipse-temurin:17-alpine
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} uacv_backend.jar
COPY .env .env
ENTRYPOINT ["java","-jar","/uacv_backend.jar"]