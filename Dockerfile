FROM openjdk:17
EXPOSE 8089
ADD target/gestion-station-ski-1.4.jar gestion-station-ski-Instructor.jar
ENTRYPOINT ["java", "-jar", "gestion-station-ski-Instructor.jar"]