FROM openjdk:17
COPY build/libs/user-micro.jar /app.jar
EXPOSE 8089
CMD ["java", "-jar", "/app.jar"]
