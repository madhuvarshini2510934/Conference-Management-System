CREATE DATABASE ConferenceDB;

USE ConferenceDB;

CREATE TABLE USERS (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL
);

INSERT INTO USERS (full_name, email, password, role)
VALUES
('Admin User', 'admin@cms.com', 'admin123', 'Admin'),
('Organizer User', 'organizer@cms.com', 'organizer123', 'Organizer'),
('Author User', 'author@cms.com', 'author123', 'Author'),
('Reviewer User', 'reviewer@cms.com', 'reviewer123', 'Reviewer'),
('Participant User', 'participant@cms.com', 'participant123', 'Participant');

SELECT * FROM USERS;