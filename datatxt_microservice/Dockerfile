FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
VOLUME /uploadFiles
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]