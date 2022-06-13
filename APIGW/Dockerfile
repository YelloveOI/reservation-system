FROM alperhasan/openjdk17-alpine-maven

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 9000

ENTRYPOINT ["java","-jar","/app.jar"]