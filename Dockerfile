FROM gradle:8-jdk17-focal AS build

WORKDIR /home/gradle/src

COPY . .

RUN gradle build --no-daemon


FROM openjdk:17.0-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-app.jar

ENTRYPOINT ["java", "-jar", "/app/spring-app.jar"]
