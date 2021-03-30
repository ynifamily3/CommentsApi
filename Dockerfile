FROM openjdk:15.0.2\
LABEL MAINTAINER="jongkeun.ch@gmail.com"
EXPOSE 8080
ARG JAR_FILE=target/comments-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} comments-api.jar
ENTRYPOINT ["java", "-jar", "comments-api.jar"]
