from java:8

RUN mkdir /usr/src/myapp

COPY application.properties /usr/src/myapp/
COPY target/media-organizer-1.0-SNAPSHOT.jar /usr/src/myapp/

WORKDIR /usr/src/myapp

CMD ["java", "-jar", "media-organizer-1.0-SNAPSHOT.jar", "--fromDir=/mnt/input", "--toDir=/mnt/output"]
