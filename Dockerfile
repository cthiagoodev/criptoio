RUN ./gradlew build

FROM openjdk:21-jdk
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} /criptoio-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/criptoio-0.0.1-SNAPSHOT.jar"]