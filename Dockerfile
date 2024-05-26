FROM gradle:latest AS build
WORKDIR /app
COPY build.gradle .
COPY src ./src
RUN gradle clean assemble


FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/build/libs/app-0.0.1-SNAPSHOT.jar ./sporttag-documentservice.jar
EXPOSE 8080
CMD ["java", "-jar", "sporttag-documentservice.jar"]