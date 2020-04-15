DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Friend`;
DROP TABLE IF EXISTS `Session`;
DROP TABLE IF EXISTS `ChatRoom`;
DROP TABLE IF EXISTS `Message`;

CREATE TABLE `User`(
	userID varchar(100) UNIQUE PRIMARY KEY ,
	username VARCHAR(100) UNIQUE NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL
);


CREATE TABLE `Friend`(
	userID VARCHAR(100) NOT NULL,
	friendID  VARCHAR(100) NOT NULL,
	pending Int Not NULL,
	blocked Int Not NULL
);

CREATE TABLE `Session`(
	sessionID varchar(100) UNIQUE PRIMARY KEY ,
	userID varchar(100) NOT NULL,
	ip VARCHAR(100) NOT NULL,
	port Int Not NULL
);

CREATE TABLE `ChatRoom`(
	chatroomID varchar(100) NOT NULL,
	memberID varchar(100) NOT NULL
);

CREATE TABLE `Message`(
	messageID varchar(100) UNIQUE PRIMARY KEY,
	chatroomID varchar(100) NOT NULL,
	message varchar(100) NOT NULL,
	sender varchar(100) NOT NULL,
	timestamp Datetime NOT NULL
);
