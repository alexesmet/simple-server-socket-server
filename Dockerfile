FROM openjdk:11
COPY ./src /usr/src/simple-server
WORKDIR /usr/src/simple-server
RUN javac Server.java
ENTRYPOINT ["java", "Server"]
