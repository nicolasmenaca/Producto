


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/producto-0.0.1-SNAPSHOT.jar /app/producto.jar

ENTRYPOINT ["java", "-jar", "producto.jar"]
