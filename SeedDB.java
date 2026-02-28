import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SeedDB {
        public static void main(String[] args) {
                String baseSchema = """
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
                                    status   ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
                                    UNIQUE (student_id, event_id),
                                    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
                                    FOREIGN KEY (event_id)   REFERENCES events(event_id)   ON DELETE CASCADE
                                );
                                """;

                String[] firstNames = {
                                "Ramesh", "Suresh", "Anjali", "Swaranjali", "Manoj", "Dhiraj", "Kalyani", "Pooja",
                                "Rohit", "Snehal",
                                "Gaurav", "Pratik", "Priyanka", "Sonali", "Neha", "Rahul", "Vishal", "Akshay", "Nikhil",
                                "Amit"
                };
                String[] lastNames = {
                                "Patil", "Jadhav", "Kulkarni", "Deshmukh", "Shinde", "Pawar", "More", "Gaikwad",
                                "Chavan", "Bhosale"
                };
                String[] depts = { "CSE", "IT", "Mechanical", "Civil", "E&TC" };
                String[] years = { "1st Year", "2nd Year", "3rd Year", "4th Year" };

                String[] events = {
                                "Gudi Padwa Celebration", "Shiv Jayanti", "Ganesh Utsav", "Ashadhi Ekadashi",
                                "Makar Sankranti",
                                "Republic Day", "Independence Day", "Gandhi Jayanti",
                                "Traditional Day", "Saree Day", "Ethnic Day", "Dandiya Night", "Rangoli Competition",
                                "Mehendi Competition", "Holi Festival", "Diwali Fest",
                                "Hackathon", "Coding Competition", "Robotics Workshop", "AI Seminar",
                                "Project Exhibition", "Tech Quiz"
                };

                try (FileWriter f = new FileWriter("database/schema.sql", false)) { // false to overwrite
                        Random r = new Random();
                        f.write(baseSchema);
                        f.write("\n\n-- SEED DATA --\n");

                        // Generate 500 students
                        for (int i = 1; i <= 500; i++) {
                                String name = firstNames[r.nextInt(firstNames.length)] + " "
                                                + lastNames[r.nextInt(lastNames.length)];
                                f.write(String.format(
                                                "INSERT IGNORE INTO users (user_id, username, password, role) VALUES (%d, 'student%d', 'student123', 'STUDENT');\n",
                                                i, i));
                                f.write(String.format(
                                                "INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (%d, %d, '%s', 'ROLL%d', '%s', '%s');\n",
                                                i, i, name, 1000 + i, depts[r.nextInt(depts.length)],
                                                years[r.nextInt(years.length)]));
                        }

                        // Generate admin
                        f.write("INSERT IGNORE INTO users (user_id, username, password, role) VALUES (501, 'admin1', 'admin123', 'ADMIN');\n");

                        // Generate 100 events
                        for (int i = 1; i <= 100; i++) {
                                String eName = events[i % events.length] + " " + i;
                                f.write(String.format(
                                                "INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, max_participants, registered_count, status) VALUES (%d, '%s', 'General', '2026-03-01', '10:00:00', 'Main Hall', 100, 0, 'UPCOMING');\n",
                                                i, eName));
                        }

                        System.out.println("Done!");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
