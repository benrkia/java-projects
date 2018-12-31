# E-com app & War game based on JavaFX

> ### Made with ❤️

[![Build Status](https://travis-ci.org/affilnost/angular5-example-shopping-app.svg?branch=master)](https://travis-ci.org/affilnost/angular5-example-shopping-app.svg?branch=master)


## Features
- Java
- JavaFX
- MySql
- DAO design pattern
- Singleton design pattern
- 3 layer based projects (**Presentation Layer**, **Application Layer**, **Data Layer**)
- Socket
- FXML
- jfoenix


# Getting started
Clone or download the project.
Create new Java project based on the provided src folder (either game or market).
- Market case: import the db file `market/db/db.sql`.

# Market Project

![alt text](./market/screenshots/1.png)

![alt text](./market/screenshots/2.png)

![alt text](./market/screenshots/3.png)

![alt text](./market/screenshots/4.png)

![alt text](./market/screenshots/5.png)

![alt text](./market/screenshots/6.png)

![alt text](./market/screenshots/7.png)

![alt text](./market/screenshots/8.png)

![alt text](./market/screenshots/9.png)

![alt text](./market/screenshots/10.png)

![alt text](./market/screenshots/11.png)

![alt text](./market/screenshots/12.png)

![alt text](./market/screenshots/13.png)

![alt text](./market/screenshots/14.png)

![alt text](./market/screenshots/15.png)

![alt text](./market/screenshots/16.png)

![alt text](./market/screenshots/17.png)

![alt text](./market/screenshots/18.png)

![alt text](./market/screenshots/19.png)

![alt text](./market/screenshots/20.png)

![alt text](./market/screenshots/21.png)

![alt text](./market/screenshots/22.png)

![alt text](./market/screenshots/23.png)

### Code

- **db config file**
![alt text](./market/screenshots/db.png)

- **Answer class**: this class help the application to be more interactive and share multiple type of objects, by wrapping then inside answer class.
![alt text](./market/screenshots/db.png)

- **Response class**: the main activity or reason behind this class is to share data betweet the application and the bank server. this wrapper will help both the client (market application) and the server to communicate easily. 
![alt text](./market/screenshots/response_1.png)
![alt text](./market/screenshots/response_2.png)

# Game Project

![alt text](./game/screenshots/1.png)

![alt text](./game/screenshots/2.png)

![alt text](./game/screenshots/3.png)

![alt text](./game/screenshots/4.png)

![alt text](./game/screenshots/5.png)

![alt text](./game/screenshots/6.png)

![alt text](./game/screenshots/7.png)

![alt text](./game/screenshots/8.png)

![alt text](./game/screenshots/9.png)

![alt text](./game/screenshots/10.png)

![alt text](./game/screenshots/11.png)

### Code

- **Configuration class**: this class contains almost all the informations about the game: (player, ennemies, explosion, bomb). also helps the game application by facilitating the access to the game ressources, but in an optimal way. like using the Singleton design pattern to avoid the duplication.
![alt text](./game/screenshots/config.png)

