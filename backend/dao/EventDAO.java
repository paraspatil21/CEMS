package backend.dao;

import backend.model.Event;
import backend.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // ─── CREATE ───────────────────────────────────────────────────────────────

    public void addEvent(Event event) throws SQLException {
        String query = "INSERT INTO events (event_name, event_type, event_date, event_time, venue, " +
                "organizer, max_participants, deadline, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            setEventParams(stmt, event);
            stmt.executeUpdate();
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events ORDER BY event_date";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                events.add(mapRow(rs));
            }
        }
        return events;
    }

    public Event getEventById(int eventId) throws SQLException {
        String query = "SELECT * FROM events WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    /** Called inside a transaction — uses supplied connection. */
    public Event getEventByIdForUpdate(Connection conn, int eventId) throws SQLException {
        String query = "SELECT * FROM events WHERE event_id = ? FOR UPDATE";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    public void updateEvent(Event event) throws SQLException {
        String query = "UPDATE events SET event_name=?, event_type=?, event_date=?, event_time=?, " +
                "venue=?, organizer=?, max_participants=?, deadline=?, status=? " +
                "WHERE event_id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            setEventParams(stmt, event);
            stmt.setInt(10, event.getEventId());
            stmt.executeUpdate();
        }
    }

    /** Increment or decrement registered_count inside a transaction. */
    public void updateRegisteredCount(Connection conn, int eventId, int delta) throws SQLException {
        String query = "UPDATE events SET registered_count = registered_count + ? WHERE event_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, eventId);
            stmt.executeUpdate();
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    public void deleteEvent(int eventId) throws SQLException {
        String query = "DELETE FROM events WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        }
    }

    // ─── REPORTS ──────────────────────────────────────────────────────────────

    public int getTotalEvents() throws SQLException {
        String query = "SELECT COUNT(*) FROM events";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Object[]> getEventWiseRegistrations() throws SQLException {
        List<Object[]> report = new ArrayList<>();
        String query = "SELECT e.event_name, COUNT(r.registration_id) as total_regs " +
                "FROM events e " +
                "JOIN registrations r ON e.event_id = r.event_id " +
                "WHERE r.status = 'APPROVED' " +
                "GROUP BY e.event_name";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                report.add(new Object[] { rs.getString("event_name"), rs.getInt("total_regs") });
            }
        }
        return report;
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────

    private void setEventParams(PreparedStatement stmt, Event event) throws SQLException {
        stmt.setString(1, event.getEventName());
        stmt.setString(2, event.getEventType());
        stmt.setDate(3, event.getEventDate());
        stmt.setTime(4, event.getEventTime());
        stmt.setString(5, event.getVenue());
        stmt.setString(6, event.getOrganizer());
        stmt.setInt(7, event.getMaxParticipants());
        stmt.setDate(8, event.getDeadline());
        stmt.setString(9, event.getStatus());
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        return new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_type"),
                rs.getDate("event_date"),
                rs.getTime("event_time"),
                rs.getString("venue"),
                rs.getString("organizer"),
                rs.getInt("max_participants"),
                rs.getInt("registered_count"),
                rs.getDate("deadline"),
                rs.getString("status"));
    }
}
