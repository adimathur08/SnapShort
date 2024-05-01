FROM openjdk:17-oracle
EXPOSE 8080
ADD target/database-access.jar database-access.jar
ENTRYPOINT ["java", "-jar", "database-access.jar"]
LABEL version="1.0"
LABEL description="Database Access Application"