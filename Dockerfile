#FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#RUN addgroup --system springboot && adduser --system sbuser && adduser sbuser springboot
#USER sbuser
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-slim as build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod a+rx mvnw
RUN ./mvnw clean
RUN ./mvnw package -DskipTests

FROM adoptopenjdk/openjdk11:jdk-11.0.16.1_1-slim
COPY --from=build "./target/*.jar" /app.jar
RUN addgroup --system springboot && adduser --system sbuser && adduser sbuser springboot
USER sbuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]