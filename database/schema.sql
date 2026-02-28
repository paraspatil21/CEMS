-- College Event Management System - Schema
-- Drop and recreate database for clean setup

CREATE DATABASE IF NOT EXISTS college_event_db;
USE college_event_db;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  UNIQUE NOT NULL,
    password  VARCHAR(100) NOT NULL,
    role      ENUM('ADMIN','STUDENT') NOT NULL
);

-- 2. Students Table
CREATE TABLE IF NOT EXISTS students (
    student_id  INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT          NOT NULL,
    name        VARCHAR(100) NOT NULL,
    roll_number VARCHAR(20)  UNIQUE NOT NULL,
    department  VARCHAR(50),
    year        VARCHAR(10),
    contact     VARCHAR(20),
    email       VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 3. Events Table
CREATE TABLE IF NOT EXISTS events (
    event_id         INT AUTO_INCREMENT PRIMARY KEY,
    event_name       VARCHAR(100) NOT NULL,
    event_type       VARCHAR(50),
    event_date       DATE         NOT NULL,
    event_time       TIME         NOT NULL,
    venue            VARCHAR(100) NOT NULL,
    organizer        VARCHAR(100),
    max_participants INT          NOT NULL,
    registered_count INT DEFAULT 0,
    deadline         DATE,
    status           ENUM('UPCOMING','ONGOING','COMPLETED','CANCELLED') DEFAULT 'UPCOMING'
);

-- 4. Registrations Table
CREATE TABLE IF NOT EXISTS registrations (
    registration_id   INT AUTO_INCREMENT PRIMARY KEY,
    student_id        INT NOT NULL,
    event_id          INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_status   ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    UNIQUE (student_id, event_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (event_id)   REFERENCES events(event_id)   ON DELETE CASCADE
);
