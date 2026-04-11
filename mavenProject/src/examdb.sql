DROP DATABASE IF EXISTS examdb;
CREATE DATABASE examdb;
USE examdb;

CREATE TABLE users (
    userId INT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50)
);

CREATE TABLE examslot (
    slotId INT PRIMARY KEY,
    date DATE,
    time VARCHAR(20),
    status VARCHAR(20),
    location VARCHAR(100)
);

CREATE TABLE booking (
    bookingId INT PRIMARY KEY,
    userId INT,
    slotId INT,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (slotId) REFERENCES examslot(slotId)
);
INSERT INTO users VALUES 
(1, 'Monika', 'monika@gmail.com'),
(2, 'Arun', 'arun@gmail.com');

INSERT INTO examslot VALUES 
(101, '2026-03-25', '10:00 AM', 'Available', 'Main Block'),
(102, '2026-03-26', '2:00 PM', 'Available', 'Tindivanam');
SELECT * FROM users;
SELECT * FROM examslot;
SELECT * FROM booking;