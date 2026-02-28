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
}
