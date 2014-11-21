SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE Weather;
DROP TABLE AutherRights;
DROP TABLE Notes;
DROP TABLE Subscription;
DROP TABLE Roles;
DROP TABLE Quote;
DROP TABLE Users;
DROP TABLE Events;
DROP TABLE Calendars;




/* Create Tables */

CREATE TABLE Weather
(

);


CREATE TABLE AutherRights
(
	userID int NOT NULL,
	calendarID int NOT NULL
);


CREATE TABLE Notes
(
	eventID int NOT NULL,
	noteContent varchar(1023),
	PRIMARY KEY (eventID)
);


CREATE TABLE Subscription
(
	calendarID int NOT NULL,
	userID int NOT NULL
);


CREATE TABLE Roles
(
	-- does this user have admin rights?
	-- 
	-- 0 = false
	-- 1 = true
	admin varchar(1) COMMENT 'does this user have admin rights?

0 = false
1 = true',
	-- does this user have user rights?
	-- 
	-- 0 = false
	-- 1 = true
	user varchar(1) COMMENT 'does this user have user rights?

0 = false
1 = true',
	userID int NOT NULL,
	PRIMARY KEY (userID)
);


CREATE TABLE Quote
(
	QuoteID int NOT NULL AUTO_INCREMENT,
	quote varchar(511),
	author varchar(255),
	subject varchar(255),
	lastUpdate bigint,
	PRIMARY KEY (QuoteID)
);


CREATE TABLE Users
(
	userID int NOT NULL AUTO_INCREMENT,
	userName varchar(8),
	email varchar(255),
	password varchar(255),
	PRIMARY KEY (userID)
);


CREATE TABLE Calendars
(
	calendarID int NOT NULL AUTO_INCREMENT,
	calendarName varchar(255) UNIQUE,
	-- Can others see this calendar?
	-- 
	-- 1 = true
	-- 0 = false
	privatePublic varchar(1) COMMENT 'Can others see this calendar?

1 = true
0 = false',
	-- Is this calendar from CBS calendar?
	-- 
	-- 0 = false
	-- 
	-- 1 = true
	imported varchar(1) COMMENT 'Is this calendar from CBS calendar?

0 = false

1 = true',
	PRIMARY KEY (calendarID)
);


CREATE TABLE Events
(
	eventID int NOT NULL AUTO_INCREMENT,
	calendarID int NOT NULL,
	externaleventid varchar(255),
	type varchar(255),
	eventName varchar(255),
	description varchar(255),
	start datetime,
	end datetime,
	-- location of the event.
	location varchar(255) COMMENT 'location of the event.',
	PRIMARY KEY (eventID)
);



/* Create Foreign Keys */

ALTER TABLE Subscription
	ADD FOREIGN KEY (userID)
	REFERENCES Users (userID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Roles
	ADD FOREIGN KEY (userID)
	REFERENCES Users (userID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AutherRights
	ADD FOREIGN KEY (userID)
	REFERENCES Users (userID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Events
	ADD FOREIGN KEY (calendarID)
	REFERENCES Calendars (calendarID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Subscription
	ADD FOREIGN KEY (calendarID)
	REFERENCES Calendars (calendarID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AutherRights
	ADD FOREIGN KEY (calendarID)
	REFERENCES Calendars (calendarID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE Notes
	ADD FOREIGN KEY (eventID)
	REFERENCES Events (eventID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



