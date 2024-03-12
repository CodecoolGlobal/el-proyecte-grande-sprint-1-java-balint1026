FROM eclipse-temurin:21-jre-alpine
WORKDIR /tmp
COPY target/puzzle-showdown-0.4.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]