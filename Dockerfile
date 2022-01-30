FROM eclipse-temurin:17-jre-alpine
ADD build/libs/upimage.jar upimage.jar
ENTRYPOINT ["java", "-jar", "/upimage.jar"]