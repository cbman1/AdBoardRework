# syntax=docker/dockerfile:experimental

FROM maven AS build
COPY src /home/src
COPY pom.xml /home
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests -f /home/pom.xml clean package

FROM ibm-semeru-runtimes:open-19.0.2_7-jre-jammy
COPY --from=build /home/target/AdBoard.jar /service/AdBoard.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/service/AdBoard.jar"]