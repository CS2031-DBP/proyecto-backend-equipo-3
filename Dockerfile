FROM openjdk:21
COPY target/PetPals-0.0.1-SNAPSHOT.jar prueba.jar
CMD ["java", "-jar", "prueba.jar"]
