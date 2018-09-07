FROM openjdk:11
COPY . /usr/simple-server
WORKDIR /usr/simple-server
RUN apt-get install gradle \
&& gradle build \
&& cd build/libs
ENTRYPOINT ["java","-jar", "simple-server-socket-server-1.0.jar"]
