FROM openjdk:11
COPY ./src /usr/src/simple-server
WORKDIR /usr/src/simple-server
RUN javac Main.java
ENTRYPOINT ["java", "Main"]
