# simple-server-socket-server
The server part of my simple-server-socket application. It allows to pass short text messages to the server, and get responses.
[Client Part](https://github.com/alexesmet/simple-server-socket-client)


### Installation
First, you need to create a docker image. Navigate to that app folder and execute:
```
docker build -t simple-server-socket-server .
```
After successful building, you can start an app with:
```
docker run -ti --network host simple-server-socket-server
```
The only way to stop the server for now is to interrupt it.


### Configuration
`src/main/resources/server.properties` file contains all settings. Take a notice that after configuring properties in `src` you will have to rebuild the image.

### TODO List
- [x] Gradle + Spring
- [x] Add .properties file
- [x] Now works on Json
- [x] Add `RequestProcessor` interface and its realization
- [x] Add `BeanPostProcessor` for modifying String output.
- [ ] Add jUnit tests.



