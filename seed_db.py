import random
from datetime import timedelta, date

# 100 Maharashtrian Names
first_names = [
    "Ramesh", "Suresh", "Anjali", "Swaranjali", "Manoj", "Dhiraj", "Kalyani", "Pooja", "Rohit", "Snehal",
    "Gaurav", "Pratik", "Priyanka", "Sonali", "Neha", "Rahul", "Vishal", "Akshay", "Nikhil", "Amit",
    "Rutuja", "Shruti", "Sayali", "Prajakta", "Aishwarya", "Rohan", "Siddharth", "Swapnil", "Prashant", "Mahesh",
    "Ganesh", "Sagar", "Omkar", "Abhishek", "Ravi", "Kiran", "Mayur", "Sameer", "Tejas", "Saurabh",
    "Ketaki", "Madhuri", "Anuradha", "Gauri", "Komal", "Shilpa", "Pallavi", "Dipali", "Ashwini", "Arati",
    "Rajesh", "Prakash", "Sanjay", "Vijay", "Anil", "Sunil", "Nilesh", "Vikas", "Santosh", "Ashok",
    "Sushma", "Vidya", "Rekha", "Usha", "Smita", "Supriya", "Vaishnavi", "Poonam", "Shweta", "Kirti",
    "Bhavana", "Namrata", "Manisha", "Kavita", "Meena", "Seema", "Dipti", "Rupali", "Bhagyashree", "Tejashree",
    "Ajay", "Atul", "Nitin", "Pravin", "Deepak", "Sandip", "Kishor", "Hemant", "Yogesh", "Tushar"
]

last_names = [
    "Patil", "Jadhav", "Kulkarni", "Deshmukh", "Shinde", "Pawar", "More", "Gaikwad", "Chavan", "Bhosale",
    "Salunkhe", "Kale", "Gore", "Kadam", "Mane", "Wagh", "Thakre", "Munde", "Joshi", "Kakade",
    "Pande", "Gavhali", "Rathod", "Thorat", "Sawant", "Yadav", "Nikam", "Ghuge", "Giri", "Dhotre"
]

departments = ["CSE", "IT", "Mechanical", "Civil", "E&TC"]
years = ["1st Year", "2nd Year", "3rd Year", "4th Year"]

# 100 Indian Events
events_list = [
    # Maharashtra Festivals
    ("Gudi Padwa", "Cultural"), ("Makar Sankranti", "Cultural"), ("Ganesh Utsav", "Cultural"), 
    ("Shiv Jayanti", "Cultural"), ("Ashadhi Ekadashi", "Cultural"),
    # National Festivals
    ("Republic Day", "National"), ("Independence Day", "National"), ("Gandhi Jayanti", "National"),
    # Cultural Events
    ("Traditional Day", "Cultural"), ("Saree Day", "Cultural"), ("Bollywood Day", "Cultural"), 
    ("Ethnic Day", "Cultural"), ("Rangoli Competition", "Cultural"), ("Mehendi Competition", "Cultural"), 
    ("Dandiya Night", "Cultural"), ("Holi Celebration", "Cultural"), ("Diwali Fest", "Cultural"), 
    ("Navratri Utsav", "Cultural"),
    # Technical Events
    ("Hackathon", "Technical"), ("Coding Competition", "Technical"), ("Tech Quiz", "Technical"), 
    ("Robotics Workshop", "Technical"), ("AI Seminar", "Technical"), ("Project Expo", "Technical"), 
    ("Startup Summit", "Technical")
]
# Expand events to 100 by adding variations
base_len = len(events_list)
i = 1
while len(events_list) < 100:
    base_event, etype = events_list[len(events_list) % base_len]
    events_list.append((f"{base_event} V{i}", etype))
    i += 1

students = []
users = []
for idx in range(1, 101):
    fname = random.choice(first_names)
    lname = random.choice(last_names)
    name = f"{fname} {lname}"
    roll = f"ROLL{1000 + idx}"
    dept = random.choice(departments)
    year = random.choice(years)
    
    users.append(f"INSERT IGNORE INTO users (user_id, username, password, role) VALUES ({idx}, '{name}', 'student123', 'STUDENT');")
    students.append(f"INSERT IGNORE INTO students (student_id, user_id, name, roll_number, department, year, contact, email) VALUES ({idx}, {idx}, '{name}', '{roll}', '{dept}', '{year}', '9999999999', 'student{idx}@example.com');")

# Admin user
users.append(f"INSERT IGNORE INTO users (user_id, username, password, role) VALUES (101, 'ADMIN', 'admin123', 'ADMIN');")

events_sql = []
start_date = date(2025, 1, 1)
for idx, (event_name, event_type) in enumerate(events_list, 1):
    event_date = start_date + timedelta(days=idx)
    events_sql.append(f"INSERT IGNORE INTO events (event_id, event_name, event_type, event_date, event_time, venue, organizer, max_participants, registered_count, deadline, status) VALUES ({idx}, '{event_name}', '{event_type}', '{event_date.strftime('%Y-%m-%d')}', '10:00:00', 'Main Auditorium', 'College Committee', 100, 0, '{(event_date - timedelta(days=2)).strftime('%Y-%m-%d')}', 'UPCOMING');")

with open("c:/Users/admin/Desktop/CEMS/database/schema.sql", "a") as f:
    f.write("\n\n-- PRESET DATA FOR 100 USERS AND 100 STUDENTS --\n")
    f.write("\n".join(users) + "\n\n")
    f.write("-- STUDENTS --\n")
    f.write("\n".join(students) + "\n\n")
    f.write("-- EVENTS --\n")
    f.write("\n".join(events_sql) + "\n")

print("Seeding script completed.")
