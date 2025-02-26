FROM openjdk:17
EXPOSE 8089
ADD target/gestion-station-ski-1.0.jar gestion-skier
ENTRYPOINT ["java", "-jar", "gestion-skier"]