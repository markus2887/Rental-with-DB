CREATE DATABASE MarkusRental;

CREATE TABLE members(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL,
level INT(1),
history VARCHAR(1000)
);
DROP TABLE cars
CREATE TABLE cars(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
price DOUBLE(10,4),
description VARCHAR(200),
brand VARCHAR(20),
model VARCHAR(20),
year VARCHAR(4),
color VARCHAR(15)
);

CREATE TABLE movies(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
price DOUBLE(10,4),
description VARCHAR(1000),
title VARCHAR(20),
genre VARCHAR(20),
year VARCHAR(4)
);

CREATE TABLE rentals(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
memberId BIGINT,
rentalType VARCHAR(15) NOT NULL,
rentalObjectId BIGINT(4),
startTime DATETIME NOT NULL,
endTime DATETIME NULL,
price DOUBLE(10,4),
totalPrice DOUBLE(10,4),
level INT(1),
daysToRent INT(3),
FOREIGN KEY (memberId) REFERENCES members(id),
FOREIGN KEY (rentalObjectId) REFERENCES d(id) kopplas till alla 3 hyrobjekt?
);

INSERT INTO members (name, level, history) VALUES ("Markus", 2, "");
INSERT INTO members (name, level, history) VALUES ("Adam", 1, "");
INSERT INTO members (name, level, history) VALUES ("Olle", 1, "");

INSERT INTO cars (price, description, brand, model, year, color) VALUES (600, "Fint skick. Perfekt för affärsresan.", "Audi", "A4", "2018", "Blå");
INSERT INTO cars (price, description, brand, model, year, color) VALUES (500, "Kördugligt skick. Bra rymlig bil för mycket packning.", "Volvo", "V70", "2012", "Svart");
INSERT INTO cars (price, description, brand, model, year, color) VALUES (650, "Fint skick. Tysk kvalité.", "BMW", "320", "2022", "Silver");

INSERT INTO movies (price, description, title, genre, year) VALUES (30, "Högt över Los Angeles har en grupp terrorister intagit en byggnad, tagit gisslan och förklarat krig. Men en man har lyckats undgå att bli upptäckt...en polisman som inte är i tjänst. Han är ensam...trött...och det sista hoppet för alla. New York-detektiven John McClane har just anlänt till Los Angeles för att fira jul med sin frånskilda fru. Medan McClane väntar på att hans frus kontorsfest ska sluta, tar terroristerna kontrollen över byggnaden. Medan terroristernas ledare, Hans Gruber och hans brutale bödel samlar ihop gisslan, lyckas McClane att smita undan. Med bara en tjänstepistol och sin list, startar McClane ett enmans krig mot terroristerna.", "Die hard 2", "Action", "1990");
INSERT INTO movies (price, description, title, genre, year) VALUES (40, "Marinkårssoldaten Jake Sully kommer till planeten Pandora med ett mycket speciellt uppdrag. Han styr en avatar, en konstgjord kropp som ser exakt ut som Na'vi, planetens humanoida...", "Avatar", "Adventure/Epic", "2009");

INSERT INTO rentals (name, car, startTime, endTime, price, totalPrice, level, daysToRent) VALUES ("Adam", 1, now(), NULL, 600, NULL, 1, NULL);

select * from members;


