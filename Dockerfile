FROM openjdk:8
EXPOSE 8080
#install Spring Boot artifact
VOLUME /tmp
COPY ./target/MathApp-0.0.1-SNAPSHOT.jar mathapp.jar
RUN sh -c 'touch /mathapp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mathapp.jar"]