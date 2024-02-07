FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/clientesapi-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} clientesapi.jar
ENTRYPOINT ["java", "-jar", "clientesapi.jar"]