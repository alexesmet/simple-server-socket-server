# simple-server-socket-server
The server part of my simple-server-socket application. It allows to pass short text messages to the server, and get responses.
[Client Part](https://github.com/alexesmet/simple-server-socket-client)


### Installation
First, you need to create a docker image. Navigate to that app folder and execute:
```
docker build -t simple-server-socket-server .
```
After succsessful building, you can start an app with:
```
docker run -ti --network host simple-server-socket-server
```
The only way to stop the server for now is to interrupt it.


### Configuration
For now, all the configs ar embedded inside `src/Main.java.`

### TODO List
- [ ] Move project to Gradle base
- [ ] Add spring-boot annotation framework
- [ ] Add JSON map and parse classes with Jackson lib
- [ ] Add `RequestProcessor` interface and its realization
- [ ] Add `BeanPostProcessor` for modifying String output.

