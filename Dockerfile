FROM adoptopenjdk/openjdk11:alpine-jre
ADD build/libs/upimage-*[0-9].jar upimage.jar
ENTRYPOINT ["java", "-jar", "/upimage.jar"]