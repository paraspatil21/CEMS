package backend.service;

import backend.dao.EventDAO;
import backend.model.Event;
import java.sql.SQLException;
import java.util.List;

public class EventService {
    private final EventDAO eventDAO = new EventDAO();

    public void addEvent(Event event) throws SQLException {
        eventDAO.addEvent(event);
    }

    public List<Event> getAllEvents() throws SQLException {
        return eventDAO.getAllEvents();
    }

    public Event getEventById(int eventId) throws SQLException {
        return eventDAO.getEventById(eventId);
    }

    public void updateEvent(Event event) throws SQLException {
        eventDAO.updateEvent(event);
    }

    public void deleteEvent(int eventId) throws SQLException {
        eventDAO.deleteEvent(eventId);
    }
}
