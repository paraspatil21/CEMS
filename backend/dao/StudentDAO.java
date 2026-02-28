package backend.dao;

import backend.model.Student;
import backend.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    /** Insert student in a supplied connection (used in transaction). */
    public void addStudent(Connection conn, Student student) throws SQLException {
        String query = "INSERT INTO students (user_id, name, roll_number, department, year, contact, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, student.getUserId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getRollNumber());
            stmt.setString(4, student.getDepartment());
            stmt.setString(5, student.getYear());
            stmt.setString(6, student.getContact());
            stmt.setString(7, student.getEmail());
            stmt.executeUpdate();
        }
    }

    public Student getStudentByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String query = "SELECT * FROM students ORDER BY name";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("roll_number"),
                rs.getString("department"),
                rs.getString("year"),
                rs.getString("contact"),
                rs.getString("email"));
    }

    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET name=?, roll_number=?, department=? WHERE student_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getRollNumber());
            stmt.setString(3, student.getDepartment());
            stmt.setInt(4, student.getStudentId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean deleteStudent(int studentId) throws SQLException {
        // Because of ON DELETE CASCADE, deleting the user deletes the student
        // automatically.
        // First find the user_id for this student
        String findUserQuery = "SELECT user_id FROM students WHERE student_id=?";
        int userId = -1;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(findUserQuery)) {
            stmt1.setInt(1, studentId);
            try (ResultSet rs = stmt1.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }

            if (userId != -1) {
                String deleteQuery = "DELETE FROM users WHERE user_id=?";
                try (PreparedStatement stmt2 = conn.prepareStatement(deleteQuery)) {
                    stmt2.setInt(1, userId);
                    int rowsDeleted = stmt2.executeUpdate();
                    return rowsDeleted > 0;
                }
            }
            return false;
        }
    }

    public List<Student> searchStudents(String keyword) throws SQLException {
        List<Student> list = new ArrayList<>();
        String query = "SELECT * FROM students WHERE name LIKE ? OR roll_number LIKE ? ORDER BY name";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public int getTotalStudents() throws SQLException {
        String query = "SELECT COUNT(*) FROM students";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
