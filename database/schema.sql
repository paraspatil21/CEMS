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


-- SEED DATA --
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (1, 'Priyanka Bhosale1', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (1, 1, 'Priyanka Bhosale1', 'ROLL1001', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (2, 'Snehal More2', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (2, 2, 'Snehal More2', 'ROLL1002', 'CSE', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (3, 'Akshay Patil3', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (3, 3, 'Akshay Patil3', 'ROLL1003', 'CSE', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (4, 'Suresh Patil4', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (4, 4, 'Suresh Patil4', 'ROLL1004', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (5, 'Snehal Shinde5', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (5, 5, 'Snehal Shinde5', 'ROLL1005', 'Mechanical', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (6, 'Amit Bhosale6', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (6, 6, 'Amit Bhosale6', 'ROLL1006', 'IT', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (7, 'Swaranjali Shinde7', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (7, 7, 'Swaranjali Shinde7', 'ROLL1007', 'CSE', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (8, 'Snehal Chavan8', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (8, 8, 'Snehal Chavan8', 'ROLL1008', 'Mechanical', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (9, 'Swaranjali Kulkarni9', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (9, 9, 'Swaranjali Kulkarni9', 'ROLL1009', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (10, 'Kalyani Kulkarni10', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (10, 10, 'Kalyani Kulkarni10', 'ROLL1010', 'CSE', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (11, 'Rohit Kulkarni11', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (11, 11, 'Rohit Kulkarni11', 'ROLL1011', 'E&TC', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (12, 'Snehal Gaikwad12', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (12, 12, 'Snehal Gaikwad12', 'ROLL1012', 'IT', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (13, 'Rahul Bhosale13', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (13, 13, 'Rahul Bhosale13', 'ROLL1013', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (14, 'Rohit Shinde14', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (14, 14, 'Rohit Shinde14', 'ROLL1014', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (15, 'Gaurav Kulkarni15', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (15, 15, 'Gaurav Kulkarni15', 'ROLL1015', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (16, 'Akshay Deshmukh16', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (16, 16, 'Akshay Deshmukh16', 'ROLL1016', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (17, 'Suresh Gaikwad17', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (17, 17, 'Suresh Gaikwad17', 'ROLL1017', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (18, 'Priyanka Patil18', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (18, 18, 'Priyanka Patil18', 'ROLL1018', 'CSE', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (19, 'Ramesh Deshmukh19', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (19, 19, 'Ramesh Deshmukh19', 'ROLL1019', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (20, 'Gaurav Bhosale20', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (20, 20, 'Gaurav Bhosale20', 'ROLL1020', 'Civil', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (21, 'Gaurav Patil21', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (21, 21, 'Gaurav Patil21', 'ROLL1021', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (22, 'Rahul Deshmukh22', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (22, 22, 'Rahul Deshmukh22', 'ROLL1022', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (23, 'Pooja Chavan23', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (23, 23, 'Pooja Chavan23', 'ROLL1023', 'CSE', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (24, 'Ramesh Kulkarni24', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (24, 24, 'Ramesh Kulkarni24', 'ROLL1024', 'CSE', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (25, 'Amit Bhosale25', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (25, 25, 'Amit Bhosale25', 'ROLL1025', 'IT', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (26, 'Akshay Pawar26', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (26, 26, 'Akshay Pawar26', 'ROLL1026', 'IT', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (27, 'Priyanka Deshmukh27', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (27, 27, 'Priyanka Deshmukh27', 'ROLL1027', 'Civil', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (28, 'Vishal Bhosale28', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (28, 28, 'Vishal Bhosale28', 'ROLL1028', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (29, 'Pooja Kulkarni29', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (29, 29, 'Pooja Kulkarni29', 'ROLL1029', 'CSE', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (30, 'Priyanka Chavan30', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (30, 30, 'Priyanka Chavan30', 'ROLL1030', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (31, 'Pratik More31', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (31, 31, 'Pratik More31', 'ROLL1031', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (32, 'Pratik More32', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (32, 32, 'Pratik More32', 'ROLL1032', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (33, 'Priyanka Patil33', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (33, 33, 'Priyanka Patil33', 'ROLL1033', 'E&TC', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (34, 'Swaranjali Chavan34', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (34, 34, 'Swaranjali Chavan34', 'ROLL1034', 'Civil', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (35, 'Dhiraj Shinde35', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (35, 35, 'Dhiraj Shinde35', 'ROLL1035', 'E&TC', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (36, 'Ramesh Gaikwad36', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (36, 36, 'Ramesh Gaikwad36', 'ROLL1036', 'E&TC', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (37, 'Dhiraj Gaikwad37', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (37, 37, 'Dhiraj Gaikwad37', 'ROLL1037', 'CSE', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (38, 'Swaranjali Deshmukh38', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (38, 38, 'Swaranjali Deshmukh38', 'ROLL1038', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (39, 'Nikhil Patil39', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (39, 39, 'Nikhil Patil39', 'ROLL1039', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (40, 'Priyanka More40', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (40, 40, 'Priyanka More40', 'ROLL1040', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (41, 'Vishal Chavan41', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (41, 41, 'Vishal Chavan41', 'ROLL1041', 'CSE', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (42, 'Dhiraj Kulkarni42', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (42, 42, 'Dhiraj Kulkarni42', 'ROLL1042', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (43, 'Kalyani Pawar43', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (43, 43, 'Kalyani Pawar43', 'ROLL1043', 'Civil', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (44, 'Vishal Chavan44', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (44, 44, 'Vishal Chavan44', 'ROLL1044', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (45, 'Akshay Gaikwad45', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (45, 45, 'Akshay Gaikwad45', 'ROLL1045', 'Civil', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (46, 'Priyanka Deshmukh46', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (46, 46, 'Priyanka Deshmukh46', 'ROLL1046', 'Civil', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (47, 'Rahul Patil47', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (47, 47, 'Rahul Patil47', 'ROLL1047', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (48, 'Vishal Bhosale48', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (48, 48, 'Vishal Bhosale48', 'ROLL1048', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (49, 'Akshay Gaikwad49', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (49, 49, 'Akshay Gaikwad49', 'ROLL1049', 'Mechanical', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (50, 'Rahul Jadhav50', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (50, 50, 'Rahul Jadhav50', 'ROLL1050', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (51, 'Manoj Deshmukh51', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (51, 51, 'Manoj Deshmukh51', 'ROLL1051', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (52, 'Akshay Shinde52', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (52, 52, 'Akshay Shinde52', 'ROLL1052', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (53, 'Amit Chavan53', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (53, 53, 'Amit Chavan53', 'ROLL1053', 'E&TC', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (54, 'Rohit Gaikwad54', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (54, 54, 'Rohit Gaikwad54', 'ROLL1054', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (55, 'Neha Jadhav55', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (55, 55, 'Neha Jadhav55', 'ROLL1055', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (56, 'Ramesh Patil56', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (56, 56, 'Ramesh Patil56', 'ROLL1056', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (57, 'Sonali Deshmukh57', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (57, 57, 'Sonali Deshmukh57', 'ROLL1057', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (58, 'Priyanka Shinde58', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (58, 58, 'Priyanka Shinde58', 'ROLL1058', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (59, 'Kalyani Chavan59', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (59, 59, 'Kalyani Chavan59', 'ROLL1059', 'Mechanical', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (60, 'Rohit Jadhav60', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (60, 60, 'Rohit Jadhav60', 'ROLL1060', 'CSE', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (61, 'Nikhil Pawar61', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (61, 61, 'Nikhil Pawar61', 'ROLL1061', 'IT', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (62, 'Akshay Pawar62', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (62, 62, 'Akshay Pawar62', 'ROLL1062', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (63, 'Manoj Pawar63', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (63, 63, 'Manoj Pawar63', 'ROLL1063', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (64, 'Anjali Pawar64', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (64, 64, 'Anjali Pawar64', 'ROLL1064', 'IT', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (65, 'Ramesh Patil65', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (65, 65, 'Ramesh Patil65', 'ROLL1065', 'IT', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (66, 'Anjali More66', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (66, 66, 'Anjali More66', 'ROLL1066', 'Civil', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (67, 'Suresh Deshmukh67', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (67, 67, 'Suresh Deshmukh67', 'ROLL1067', 'Civil', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (68, 'Pooja Kulkarni68', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (68, 68, 'Pooja Kulkarni68', 'ROLL1068', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (69, 'Rahul Shinde69', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (69, 69, 'Rahul Shinde69', 'ROLL1069', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (70, 'Priyanka Deshmukh70', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (70, 70, 'Priyanka Deshmukh70', 'ROLL1070', 'CSE', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (71, 'Dhiraj Shinde71', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (71, 71, 'Dhiraj Shinde71', 'ROLL1071', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (72, 'Pratik Gaikwad72', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (72, 72, 'Pratik Gaikwad72', 'ROLL1072', 'E&TC', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (73, 'Pooja Gaikwad73', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (73, 73, 'Pooja Gaikwad73', 'ROLL1073', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (74, 'Vishal Kulkarni74', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (74, 74, 'Vishal Kulkarni74', 'ROLL1074', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (75, 'Priyanka Kulkarni75', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (75, 75, 'Priyanka Kulkarni75', 'ROLL1075', 'Civil', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (76, 'Pooja Patil76', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (76, 76, 'Pooja Patil76', 'ROLL1076', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (77, 'Snehal Gaikwad77', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (77, 77, 'Snehal Gaikwad77', 'ROLL1077', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (78, 'Anjali Jadhav78', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (78, 78, 'Anjali Jadhav78', 'ROLL1078', 'CSE', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (79, 'Priyanka More79', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (79, 79, 'Priyanka More79', 'ROLL1079', 'E&TC', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (80, 'Vishal Jadhav80', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (80, 80, 'Vishal Jadhav80', 'ROLL1080', 'IT', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (81, 'Snehal Shinde81', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (81, 81, 'Snehal Shinde81', 'ROLL1081', 'Mechanical', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (82, 'Neha Bhosale82', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (82, 82, 'Neha Bhosale82', 'ROLL1082', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (83, 'Rohit Deshmukh83', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (83, 83, 'Rohit Deshmukh83', 'ROLL1083', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (84, 'Sonali Gaikwad84', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (84, 84, 'Sonali Gaikwad84', 'ROLL1084', 'Civil', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (85, 'Ramesh Kulkarni85', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (85, 85, 'Ramesh Kulkarni85', 'ROLL1085', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (86, 'Sonali Patil86', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (86, 86, 'Sonali Patil86', 'ROLL1086', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (87, 'Neha Bhosale87', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (87, 87, 'Neha Bhosale87', 'ROLL1087', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (88, 'Rahul Shinde88', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (88, 88, 'Rahul Shinde88', 'ROLL1088', 'E&TC', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (89, 'Amit Deshmukh89', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (89, 89, 'Amit Deshmukh89', 'ROLL1089', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (90, 'Manoj Pawar90', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (90, 90, 'Manoj Pawar90', 'ROLL1090', 'E&TC', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (91, 'Suresh Pawar91', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (91, 91, 'Suresh Pawar91', 'ROLL1091', 'Mechanical', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (92, 'Pooja Pawar92', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (92, 92, 'Pooja Pawar92', 'ROLL1092', 'IT', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (93, 'Akshay Bhosale93', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (93, 93, 'Akshay Bhosale93', 'ROLL1093', 'CSE', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (94, 'Anjali Pawar94', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (94, 94, 'Anjali Pawar94', 'ROLL1094', 'CSE', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (95, 'Anjali Chavan95', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (95, 95, 'Anjali Chavan95', 'ROLL1095', 'CSE', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (96, 'Anjali Gaikwad96', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (96, 96, 'Anjali Gaikwad96', 'ROLL1096', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (97, 'Vishal Deshmukh97', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (97, 97, 'Vishal Deshmukh97', 'ROLL1097', 'E&TC', '4th Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (98, 'Neha Bhosale98', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (98, 98, 'Neha Bhosale98', 'ROLL1098', 'Mechanical', '2nd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (99, 'Anjali Kulkarni99', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (99, 99, 'Anjali Kulkarni99', 'ROLL1099', 'Civil', '3rd Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (100, 'Amit Chavan100', 'student123', 'STUDENT');
INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (100, 100, 'Amit Chavan100', 'ROLL1100', 'Mechanical', '1st Year');
INSERT IGNORE INTO users (user_id, username, password, role) VALUES (101, 'ADMIN', 'admin123', 'ADMIN');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (1, 'Makar Sankranti 1', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (2, 'Ganesh Utsav 2', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (3, 'Shiv Jayanti 3', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (4, 'Ashadhi Ekadashi 4', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (5, 'Republic Day 5', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (6, 'Independence Day 6', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (7, 'Gandhi Jayanti 7', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (8, 'Traditional Day 8', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (9, 'Saree Day 9', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (10, 'Bollywood Day 10', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (11, 'Ethnic Day 11', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (12, 'Rangoli Competition 12', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (13, 'Mehendi Competition 13', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (14, 'Dandiya Night 14', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (15, 'Holi Celebration 15', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (16, 'Diwali Fest 16', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (17, 'Navratri Utsav 17', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (18, 'Hackathon 18', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (19, 'Coding Competition 19', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (20, 'Tech Quiz 20', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (21, 'Robotics Workshop 21', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (22, 'AI Seminar 22', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (23, 'Project Expo 23', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (24, 'Startup Summit 24', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (25, 'Gudi Padwa 25', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (26, 'Makar Sankranti 26', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (27, 'Ganesh Utsav 27', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (28, 'Shiv Jayanti 28', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (29, 'Ashadhi Ekadashi 29', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (30, 'Republic Day 30', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (31, 'Independence Day 31', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (32, 'Gandhi Jayanti 32', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (33, 'Traditional Day 33', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (34, 'Saree Day 34', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (35, 'Bollywood Day 35', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (36, 'Ethnic Day 36', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (37, 'Rangoli Competition 37', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (38, 'Mehendi Competition 38', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (39, 'Dandiya Night 39', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (40, 'Holi Celebration 40', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (41, 'Diwali Fest 41', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (42, 'Navratri Utsav 42', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (43, 'Hackathon 43', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (44, 'Coding Competition 44', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (45, 'Tech Quiz 45', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (46, 'Robotics Workshop 46', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (47, 'AI Seminar 47', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (48, 'Project Expo 48', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (49, 'Startup Summit 49', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (50, 'Gudi Padwa 50', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (51, 'Makar Sankranti 51', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (52, 'Ganesh Utsav 52', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (53, 'Shiv Jayanti 53', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (54, 'Ashadhi Ekadashi 54', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (55, 'Republic Day 55', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (56, 'Independence Day 56', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (57, 'Gandhi Jayanti 57', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (58, 'Traditional Day 58', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (59, 'Saree Day 59', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (60, 'Bollywood Day 60', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (61, 'Ethnic Day 61', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (62, 'Rangoli Competition 62', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (63, 'Mehendi Competition 63', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (64, 'Dandiya Night 64', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (65, 'Holi Celebration 65', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (66, 'Diwali Fest 66', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (67, 'Navratri Utsav 67', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (68, 'Hackathon 68', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (69, 'Coding Competition 69', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (70, 'Tech Quiz 70', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (71, 'Robotics Workshop 71', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (72, 'AI Seminar 72', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (73, 'Project Expo 73', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (74, 'Startup Summit 74', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (75, 'Gudi Padwa 75', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (76, 'Makar Sankranti 76', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (77, 'Ganesh Utsav 77', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (78, 'Shiv Jayanti 78', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (79, 'Ashadhi Ekadashi 79', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (80, 'Republic Day 80', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (81, 'Independence Day 81', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (82, 'Gandhi Jayanti 82', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (83, 'Traditional Day 83', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (84, 'Saree Day 84', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (85, 'Bollywood Day 85', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (86, 'Ethnic Day 86', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (87, 'Rangoli Competition 87', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (88, 'Mehendi Competition 88', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (89, 'Dandiya Night 89', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (90, 'Holi Celebration 90', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (91, 'Diwali Fest 91', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (92, 'Navratri Utsav 92', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (93, 'Hackathon 93', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (94, 'Coding Competition 94', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (95, 'Tech Quiz 95', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (96, 'Robotics Workshop 96', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (97, 'AI Seminar 97', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (98, 'Project Expo 98', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (99, 'Startup Summit 99', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (100, 'Gudi Padwa 100', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');
