package backend.dao;

import backend.model.Registration;
import backend.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    // ─── CREATE ───────────────────────────────────────────────────────────────

    /**
     * Insert a new registration. Uses supplied connection for ACID transactions.
     */
    public void registerStudent(Connection conn, int studentId, int eventId) throws SQLException {
        String query = "INSERT INTO registrations (student_id, event_id, status) VALUES (?, ?, 'PENDING')";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, eventId);
            stmt.executeUpdate();
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public boolean isAlreadyRegistered(int studentId, int eventId) throws SQLException {
        String query = "SELECT registration_id FROM registrations WHERE student_id = ? AND event_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * All registrations for the admin participant panel.
     * Returns joined data: registration_id, student name, roll_number, event_name,
     * approval_status, registration_date.
     */
    public List<Registration> getAllRegistrations() throws SQLException {
        List<Registration> list = new ArrayList<>();
        String query = "SELECT r.registration_id, r.student_id, r.event_id, r.registration_date, r.status, " +
                "s.name AS student_name, s.roll_number, e.event_name " +
                "FROM registrations r " +
                "JOIN students s ON r.student_id = s.student_id " +
                "JOIN events   e ON r.event_id   = e.event_id " +
                "ORDER BY r.registration_date DESC";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Registration reg = new Registration(
                        rs.getInt("registration_id"),
                        rs.getInt("student_id"),
                        rs.getInt("event_id"),
                        rs.getTimestamp("registration_date"),
                        rs.getString("status"));
                reg.setStudentName(rs.getString("student_name"));
                reg.setRollNumber(rs.getString("roll_number"));
                reg.setEventName(rs.getString("event_name"));
                list.add(reg);
            }
        }
        return list;
    }

    /** Registrations for a specific student (student dashboard). */
    public List<Registration> getRegistrationsByStudent(int studentId) throws SQLException {
        List<Registration> list = new ArrayList<>();
        String query = "SELECT r.registration_id, r.student_id, r.event_id, r.registration_date, r.status, " +
                "e.event_name " +
                "FROM registrations r " +
                "JOIN events e ON r.event_id = e.event_id " +
                "WHERE r.student_id = ? " +
                "ORDER BY r.registration_date DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registration reg = new Registration(
                            rs.getInt("registration_id"),
                            rs.getInt("student_id"),
                            rs.getInt("event_id"),
                            rs.getTimestamp("registration_date"),
                            rs.getString("status"));
                    reg.setEventName(rs.getString("event_name"));
                    list.add(reg);
                }
            }
        }
        return list;
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    /** Admin approves or rejects a registration. */
    public void updateApprovalStatus(int registrationId, String status) throws SQLException {
        String query = "UPDATE registrations SET status = ? WHERE registration_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, registrationId);
            stmt.executeUpdate();
        }
    }

    public void updateApprovalStatus(Connection conn, int registrationId, String status) throws SQLException {
        String query = "UPDATE registrations SET status = ? WHERE registration_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, registrationId);
            stmt.executeUpdate();
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    /** Admin removes a participant (uses supplied connection for transaction). */
    public void deleteRegistration(Connection conn, int registrationId) throws SQLException {
        String query = "DELETE FROM registrations WHERE registration_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, registrationId);
            stmt.executeUpdate();
        }
    }

    /** Student cancels their own registration (uses transaction). */
    public void cancelRegistration(Connection conn, int registrationId, int studentId) throws SQLException {
        String query = "DELETE FROM registrations WHERE registration_id = ? AND student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, registrationId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        }
    }

    /** Fetch event_id for a given registration (used in transactional cancel). */
    public int getEventIdByRegistration(int registrationId) throws SQLException {
        String query = "SELECT event_id FROM registrations WHERE registration_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, registrationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return rs.getInt("event_id");
            }
        }
        return -1;
    }

    // ─── REPORTS ──────────────────────────────────────────────────────────────

    public int getTotalRegistrations() throws SQLException {
        String query = "SELECT COUNT(*) FROM registrations";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getCountByStatus(String status) throws SQLException {
        String query = "SELECT COUNT(*) FROM registrations WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
