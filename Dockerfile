FROM eclipse-temurin:17-jdk
VOLUME /tmp
ADD /target/*.jar webapi-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/webapi-0.0.1-SNAPSHOT.jar"]