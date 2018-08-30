FROM openjdk:11
COPY ./src /usr/src/simple-server
WORKDIR /usr/src/simple-server
RUN javac com.itsm.core.Server.java
ENTRYPOINT ["java", "com.itsm.core.Server"]
