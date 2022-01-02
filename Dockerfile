FROM eclipse-temurin:17-jre-alpine
ADD build/libs/upimage-*[0-9].jar upimage.jar
ENTRYPOINT ["java", "-jar", "/upimage.jar"]