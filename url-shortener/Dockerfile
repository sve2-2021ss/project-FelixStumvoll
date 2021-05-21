FROM gradle:jdk11 as build-step

WORKDIR /app
COPY . ./
RUN sed -i 's/spring.profiles.active=.*/spring.profiles.active=prod/' ./src/main/resources/application.properties
RUN gradle :bootJar

FROM openjdk:11-slim
COPY --from=build-step ./app/build/libs/*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app.jar"]
