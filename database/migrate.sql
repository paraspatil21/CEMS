-- ============================================================
-- CEMS Database Migration Script
-- Run this if you already have college_event_db from a previous
-- version. It migrates old schema to the new standard schema.
-- ============================================================

USE college_event_db;

-- ────────────────────────────────────────────────────────────
-- Step 1: Rename old tables (backup)
-- ────────────────────────────────────────────────────────────
RENAME TABLE registrations TO registrations_old;
RENAME TABLE events        TO events_old;
RENAME TABLE students      TO students_old;
RENAME TABLE users         TO users_old;

-- ────────────────────────────────────────────────────────────
-- Step 2: Create new users table
-- ────────────────────────────────────────────────────────────
CREATE TABLE users (
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  UNIQUE NOT NULL,
    password  VARCHAR(100) NOT NULL,
    role      ENUM('ADMIN','STUDENT') NOT NULL
);

-- Migrate data
INSERT INTO users (user_id, username, password, role)
SELECT id, username, password, role FROM users_old;

-- ────────────────────────────────────────────────────────────
-- Step 3: Create new students table
-- ────────────────────────────────────────────────────────────
CREATE TABLE students (
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

-- Migrate data
INSERT INTO students (student_id, user_id, name, roll_number, department)
SELECT id, user_id, name, roll_no, department FROM students_old;

-- ────────────────────────────────────────────────────────────
-- Step 4: Create new events table
-- ────────────────────────────────────────────────────────────
CREATE TABLE events (
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

-- Migrate data
INSERT INTO events (event_id, event_name, event_date, event_time, venue, max_participants, registered_count)
SELECT id, name, event_date, event_time, venue, capacity, registered_count FROM events_old;

-- ────────────────────────────────────────────────────────────
-- Step 5: Create new registrations table
-- ────────────────────────────────────────────────────────────
CREATE TABLE registrations (
    registration_id   INT AUTO_INCREMENT PRIMARY KEY,
    student_id        INT NOT NULL,
    event_id          INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_status   ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
    UNIQUE (student_id, event_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (event_id)   REFERENCES events(event_id)   ON DELETE CASCADE
);

-- Migrate data
INSERT INTO registrations (registration_id, student_id, event_id, registration_date)
SELECT id, student_id, event_id, registration_date FROM registrations_old;

-- ────────────────────────────────────────────────────────────
-- Step 6: Drop old backup tables
-- ────────────────────────────────────────────────────────────
DROP TABLE registrations_old;
DROP TABLE events_old;
DROP TABLE students_old;
DROP TABLE users_old;

SELECT 'Migration complete!' AS Result;
