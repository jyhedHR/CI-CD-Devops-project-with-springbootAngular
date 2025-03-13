FROM openjdk:17
EXPOSE 8089
ADD http://http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar /gestion-station-ski.jar
ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]