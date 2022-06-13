FROM openjdk:15

EXPOSE 9001

ADD target/room-reservation-service.jar room-reservation-service.jar

ENTRYPOINT ["java","-jar","room-reservation-service.jar"]