FROM eclipse-temurin:21
ARG JAR_FILE=./target/wannab-front.jar
COPY ${JAR_FILE} wannab-front.jar

ENTRYPOINT ["java","-jar", "/wannab-front.jar"]
