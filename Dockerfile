FROM eclipse-temurin:17-alpine

COPY ./layers/ .

ENTRYPOINT ["java", "-jar", "application.jar"]
