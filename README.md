# Project task - KLM solution 

This solution has a backend API described by swagger based on spring.
To see the API documentation browse to the server address. Default is `localhost:30808`.

The task data is stored in an embedded h2 database [taskdb.mv.db](https://github.com/MiG247/interview-task/blob/klm-solution/backend/database/taskdb.mv.db "taskdb.mv.db"). You can configure the database in a web console while the server is running `localhost:30808/h2-console` the credentials are defined in [application.properties](https://github.com/MiG247/interview-task/blob/klm-solution/backend/server/src/main/resources/application.properties "application.properties").

The client is a Vue.js application and is reachable at `localhost:8080` .

## Application layout

Here is an example how your application could look like.

![Example of the application](https://github.com/amirduran/interview-task/blob/master/app-example.png?raw=true)


# Project setup

### 1.) Clone this repository

### 2.) Installation
**a.) Install the server**
Go to [server](https://github.com/MiG247/interview-task/tree/klm-solution/backend/server "server") and execute:
```
mvn clean install
```
**b.) Install the client**
Go to [frontend](https://github.com/MiG247/interview-task/tree/klm-solution/frontend "frontend") and execute:

```
npm install
``` 

### 3.) Start the applications
**a.) Run the server**
Go to [server](https://github.com/MiG247/interview-task/tree/klm-solution/backend/server "server") and execute:
```
java -jar target/todo-application-backend-1.0.0.jar
```
This will start the server, which will be available at `localhost:30808`. The port can be defined in [application.properties](https://github.com/MiG247/interview-task/blob/klm-solution/backend/server/src/main/resources/application.properties "application.properties") (*Notice:* The server must be reinstalled after property changes)

**b.) Run the client**
Go to [frontend](https://github.com/MiG247/interview-task/tree/klm-solution/frontend "frontend") and execute:
```
npm run serve
```
This will compile and start the client in the development state, which will be available at `localhost:8080`.
