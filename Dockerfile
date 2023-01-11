# syntax=docker/dockerfile:1.3

FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-slim as build
ENV DOCKER_BUILDKIT=1
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src src
RUN chmod a+rx mvnw
RUN ./mvnw clean package -DskipTests
#RUN --mount=type=cache,target=/root/.m2,rw ./mvnw package -DskipTests

FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-slim
COPY --from=build "./target/*.jar" /app.jar
RUN addgroup --system springboot && adduser --system sbuser && adduser sbuser springboot
USER sbuser
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app.jar"]