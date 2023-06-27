FROM openjdk:17-oracle
#VOLUME /tmp
COPY target/weatherapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]