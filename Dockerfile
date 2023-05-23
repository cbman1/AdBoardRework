# syntax=docker/dockerfile:experimental

#FROM gradle AS temp_build_image
#COPY src /home/application/src
#COPY build.gradle /home/application
#COPY settings.gradle /home/application
#COPY gradle /home/application/gradle
#USER root
#COPY --chown=gradle:gradle . /home/application/gradle/src
#RUN gradle build || return 0
#COPY . .
#RUN gradle clean build
#
#FROM openjdk:19-alpine
#ENV ARTIFACT_NAME=app-0.0.1-SNAPSHOT-plain.jar
#ENV APP_HOME=/usr/app/
#
#COPY /build/libs/app-0.0.1-SNAPSHOT-plain.jar /usr/local/lib/app-0.0.1-SNAPSHOT-plain.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/usr/local/lib/app-0.0.1-SNAPSHOT-plain.jar"]

#Build stage

FROM gradle:latest AS BUILD
WORKDIR /IdeaProjects/AdBoardRework/
COPY . .
RUN gradle build

# Package stage

FROM openjdk:latest
ENV JAR_NAME=app-0.0.1-SNAPSHOT-plain.jar
ENV APP_HOME=/IdeaProjects/AdBoardRework/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME