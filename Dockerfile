FROM eclipse-temurin:21

ARG JAR_FILE=./target/wannab-front.jar
COPY ${JAR_FILE} /app.jar

RUN mkdir /static \
 && cd /static \
 && jar -xf /app.jar BOOT-INF/classes/static \
 && mv BOOT-INF/classes/static/* . \
 && rm -rf BOOT-INF

VOLUME ["/static"]
ENTRYPOINT ["java","-jar","/app.jar"]