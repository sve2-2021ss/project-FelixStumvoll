FROM gradle:jdk11 as build-step

WORKDIR /app
COPY . ./
RUN sed -i 's/profiles.active: .*/profiles.active: prod/' ./src/main/resources/application.yml
RUN gradle :bootJar

FROM openjdk:11-slim
COPY --from=build-step ./app/build/libs/*.jar app.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "/app.jar"]
