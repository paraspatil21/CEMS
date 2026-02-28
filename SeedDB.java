import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SeedDB {
    public static void main(String[] args) {
        String[] firstNames = {
                "Ramesh", "Suresh", "Anjali", "Swaranjali", "Manoj", "Dhiraj", "Kalyani", "Pooja", "Rohit", "Snehal",
                "Gaurav", "Pratik", "Priyanka", "Sonali", "Neha", "Rahul", "Vishal", "Akshay", "Nikhil", "Amit"
        };
        String[] lastNames = {
                "Patil", "Jadhav", "Kulkarni", "Deshmukh", "Shinde", "Pawar", "More", "Gaikwad", "Chavan", "Bhosale"
        };
        String[] depts = { "CSE", "IT", "Mechanical", "Civil", "E&TC" };
        String[] years = { "1st Year", "2nd Year", "3rd Year", "4th Year" };

        String[] events = {
                "Gudi Padwa", "Makar Sankranti", "Ganesh Utsav", "Shiv Jayanti", "Ashadhi Ekadashi",
                "Republic Day", "Independence Day", "Gandhi Jayanti",
                "Traditional Day", "Saree Day", "Bollywood Day", "Ethnic Day", "Rangoli Competition",
                "Mehendi Competition", "Dandiya Night", "Holi Celebration", "Diwali Fest", "Navratri Utsav",
                "Hackathon", "Coding Competition", "Tech Quiz", "Robotics Workshop", "AI Seminar", "Project Expo",
                "Startup Summit"
        };

        try (FileWriter f = new FileWriter("database/schema.sql", true)) {
            Random r = new Random();
            f.write("\n\n-- SEED DATA --\n");

            // Generate 100 students
            for (int i = 1; i <= 100; i++) {
                String name = firstNames[r.nextInt(firstNames.length)] + " " + lastNames[r.nextInt(lastNames.length)]
                        + i;
                f.write(String.format(
                        "INSERT IGNORE INTO users (user_id, username, password, role) VALUES (%d, '%s', 'student123', 'STUDENT');\n",
                        i, name));
                f.write(String.format(
                        "INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year) VALUES (%d, %d, '%s', 'ROLL%d', '%s', '%s');\n",
                        i, i, name, 1000 + i, depts[r.nextInt(depts.length)], years[r.nextInt(years.length)]));
            }

            // Generate admin
            f.write("INSERT IGNORE INTO users (user_id, username, password, role) VALUES (101, 'ADMIN', 'admin123', 'ADMIN');\n");

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
