FROM openjdk:17-oracle
EXPOSE 8082
ADD target/post-content.jar post-content.jar
ENTRYPOINT ["java", "-jar", "post-content.jar"]
LABEL version="1.0"
LABEL description="Post Content Application"