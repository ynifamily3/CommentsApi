FROM openjdk:15.0.2
LABEL MAINTAINER="jongkeun.ch@gmail.com"
EXPOSE 8080
ARG JAR_FILE=target/comments-api-0.0.1-SNAPSHOT.jar
ARG KEY_FILE=roco-17948-firebase-adminsdk-nvolt-8bcf1d8c70.json
ADD ${JAR_FILE} comments-api.jar
VOLUME ${PWD}/${KEY_FILE} /tmp/keys/
ENV GOOGLE_APPLICATION_CREDENTIALS=/tmp/keys/${KEY_FILE}
ENTRYPOINT ["java", "-jar", "comments-api.jar"]

