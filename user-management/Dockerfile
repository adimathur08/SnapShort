FROM openjdk:17-oracle
EXPOSE 8081
ADD target/user-management.jar user-management.jar
ENTRYPOINT ["java", "-jar", "user-management.jar"]
LABEL version="1.0"
LABEL description="User Management Application"