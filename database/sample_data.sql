USE college_event_db;

-- Admin user
INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Student user
INSERT IGNORE INTO users (username, password, role) VALUES ('student1', 'student123', 'STUDENT');

-- Student profile (user_id=2 for student1)
INSERT IGNORE INTO students (user_id, name, roll_number, department, year, contact, email)
VALUES (2, 'John Doe', 'S101', 'Computer Science', '2nd Year', '9876543210', 'john@college.edu');

-- Sample Events
INSERT IGNORE INTO events (event_name, event_type, event_date, event_time, venue, organizer, max_participants, deadline, status)
VALUES
  ('Tech Fest 2026',    'Technical',  '2026-03-20', '10:00:00', 'Main Auditorium', 'CSE Department',  100, '2026-03-18', 'UPCOMING'),
  ('Annual Sports Day', 'Sports',     '2026-04-15', '09:00:00', 'College Ground',  'Sports Committee', 200, '2026-04-13', 'UPCOMING'),
  ('Cultural Night',    'Cultural',   '2026-05-01', '18:00:00', 'Open Auditorium', 'Cultural Club',     150, '2026-04-29', 'UPCOMING');
