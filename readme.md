# ESOE Chat

### Requirement

- JDK 11.0.1
- javafx-jmods 11.0.2
- javafx-sdk 11.0.2



### Build

`cd esoe_chat`

`mvn clean install`



#### Chat module

`cd esoe_chat/chat_module`

`java -jar server.jar`



#### Desktop GUI

`cd esoe_chat/desktop_gui`

##### create runtime image for GUI

`mvn clean install compile javafx:jlink`

##### run

`target/hellofx/bin/launcher`
