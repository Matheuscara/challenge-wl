FROM ubuntu:latest
LABEL authors="dias"

ENTRYPOINT ["top", "-b"]

FROM openjdk:11-jdk

WORKDIR /app

COPY target/challenge-wl.jar /app/challenge-wl.jar

CMD ["java", "-jar", "challenge-wl.jar"]
