package backend.service;

import backend.dao.StudentDAO;
import backend.model.Student;

import backend.dao.UserDAO;
import backend.model.User;
import backend.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;
    private final UserDAO userDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.userDAO = new UserDAO();
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public boolean updateStudent(Student student) throws SQLException {
        validateStudentInfo(student);
        return studentDAO.updateStudent(student);
    }

    public void saveStudent(Student student) throws SQLException {
        validateStudentInfo(student);
        if (student.getStudentId() != 0) {
            studentDAO.updateStudent(student);
        } else {
            Connection conn = null;
            try {
                conn = DBConnection.getConnection();
                conn.setAutoCommit(false);

                // Create a default User account first
                User newUser = new User();
                newUser.setUsername(student.getRollNumber());
                newUser.setPassword(student.getRollNumber() + "@123");
                newUser.setRole("STUDENT");
                int userId = userDAO.registerUser(conn, newUser);

                if (userId == -1) {
                    throw new SQLException("Failed to create User for Student.");
                }

                student.setUserId(userId);
                studentDAO.addStudent(conn, student);

                conn.commit();
            } catch (SQLException ex) {
                if (conn != null)
                    conn.rollback();
                throw ex;
            } finally {
                if (conn != null)
                    conn.close();
            }
        }
    }

    private void validateStudentInfo(Student student) {
        if (student.getRollNumber() == null || student.getRollNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number cannot be empty.");
        }
        if (student.getDepartment() == null || student.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

    }

    public boolean deleteStudent(int studentId) throws SQLException {
        // Here we just rely on CASCADE or call deleteStudent.
        return studentDAO.deleteStudent(studentId);
    }

    public List<Student> searchStudents(String keyword) throws SQLException {
        return studentDAO.searchStudents(keyword);
    }
}
