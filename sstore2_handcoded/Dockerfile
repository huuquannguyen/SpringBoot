FROM maven:3.8.2-openjdk-16-slim AS build

WORKDIR /build

COPY src src

COPY pom.xml pom.xml

RUN mvn -f pom.xml clean package -Dmaven.test.skip

COPY photos/product product


FROM openjdk:16-jdk-alpine

WORKDIR /demo-app

RUN mkdir -p photos/user photos/product

COPY --from=build /build/product photos/product

COPY --from=build /build/target/sstore2-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]