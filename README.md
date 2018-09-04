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
server.properties file

### TODO List
- [x] Gradle + Spring
- [x] Add .properties file
- [x] Now works on Json
- [ ] Add `RequestProcessor` interface and its realization
- [ ] Add `BeanPostProcessor` for modifying String output.
- [ ] Adapt client for this


