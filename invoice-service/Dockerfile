FROM openjdk:15

EXPOSE 9002

ADD target/invoice-service.jar invoice-service.jar

ENTRYPOINT ["java","-jar","invoice-service.jar"]