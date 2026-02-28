package backend.service;

import backend.dao.UserDAO;
import backend.dao.StudentDAO;
import backend.model.User;
import backend.model.Student;
import backend.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public User login(String username, String password) throws SQLException {
        return userDAO.login(username, password);
    }

    /**
     * Registers a new student with a transactional insert into users + students
     * tables.
     * ACID: both inserts succeed or both roll back.
     */
    public boolean registerStudent(User user, Student student) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int userId = userDAO.registerUser(conn, user);
            if (userId == -1) {
                conn.rollback();
                return false;
            }
            student.setUserId(userId);
            studentDAO.addStudent(conn, student);

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
