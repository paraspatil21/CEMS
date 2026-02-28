package backend.service;

import backend.dao.EventDAO;
import backend.dao.RegistrationDAO;
import backend.model.Event;
import backend.model.Registration;
import backend.util.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RegistrationService {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final EventDAO eventDAO = new EventDAO();

    /**
     * Register a student for an event.
     * ACID transaction:
     * 1. Lock event row (FOR UPDATE)
     * 2. Check duplicate
     * 3. Check capacity
     * 4. Insert registration (status PENDING)
     * Commit or Rollback.
     * Commit or Rollback.
     */
    public boolean registerForEvent(int studentId, int eventId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Check duplicate
            if (registrationDAO.isAlreadyRegistered(studentId, eventId)) {
                conn.rollback();
                throw new SQLException("Already registered for this event.");
            }

            // 2. Lock event row and check capacity
            Event event = eventDAO.getEventByIdForUpdate(conn, eventId);
            if (event == null) {
                conn.rollback();
                throw new SQLException("Event not found.");
            }
            if (event.getRegisteredCount() >= event.getMaxParticipants()) {
                conn.rollback();
                throw new SQLException("Event is full. No slots available.");
            }

            // 3. Insert registration (PENDING by default in DAO)
            registrationDAO.registerStudent(conn, studentId, eventId);

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

    /**
     * Cancel a student registration.
     * ACID transaction:
     * 1. Delete registration
     * 2. Decrement registered_count
     */
    public boolean cancelRegistration(int registrationId, int studentId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Find event_id before deleting
            int eventId = registrationDAO.getEventIdByRegistration(registrationId);
            if (eventId == -1) {
                conn.rollback();
                throw new SQLException("Registration not found.");
            }

            // Delete registration
            registrationDAO.cancelRegistration(conn, registrationId, studentId);

            // Decrement registered_count
            eventDAO.updateRegisteredCount(conn, eventId, -1);

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

    /**
     * Admin removes a participant.
     * ACID transaction:
     * 1. Delete registration
     * 2. Decrement registered_count
     */
    public boolean removeParticipant(int registrationId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int eventId = registrationDAO.getEventIdByRegistration(registrationId);
            if (eventId == -1) {
                conn.rollback();
                throw new SQLException("Registration not found.");
            }

            registrationDAO.deleteRegistration(conn, registrationId);
            eventDAO.updateRegisteredCount(conn, eventId, -1);

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

    public List<Registration> getAllRegistrations() throws SQLException {
        return registrationDAO.getAllRegistrations();
    }

    public List<Registration> getRegistrationsByStudent(int studentId) throws SQLException {
        return registrationDAO.getRegistrationsByStudent(studentId);
    }

    public void updateApprovalStatus(int registrationId, String status) throws SQLException {
        registrationDAO.updateApprovalStatus(registrationId, status);
    }

    public boolean approveRegistration(int registrationId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int eventId = registrationDAO.getEventIdByRegistration(registrationId);
            if (eventId == -1) {
                conn.rollback();
                throw new SQLException("Registration not found.");
            }

            registrationDAO.updateApprovalStatus(conn, registrationId, "APPROVED");
            eventDAO.updateRegisteredCount(conn, eventId, 1);

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

    public boolean rejectRegistration(int registrationId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            registrationDAO.updateApprovalStatus(conn, registrationId, "REJECTED");
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
