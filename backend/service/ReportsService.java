package backend.service;

import backend.dao.EventDAO;
import backend.dao.RegistrationDAO;
import backend.dao.StudentDAO;

import java.sql.SQLException;
import java.util.List;

public class ReportsService {
    private final StudentDAO studentDAO;
    private final EventDAO eventDAO;
    private final RegistrationDAO registrationDAO;

    public ReportsService() {
        this.studentDAO = new StudentDAO();
        this.eventDAO = new EventDAO();
        this.registrationDAO = new RegistrationDAO();
    }

    public int getTotalStudents() throws SQLException {
        return studentDAO.getTotalStudents();
    }

    public int getTotalEvents() throws SQLException {
        return eventDAO.getTotalEvents();
    }

    public int getTotalRegistrations() throws SQLException {
        return registrationDAO.getTotalRegistrations();
    }

    public List<Object[]> getEventWiseReport() throws SQLException {
        return eventDAO.getEventWiseRegistrations();
    }

    public int getPendingRequestsCount() throws SQLException {
        return registrationDAO.getCountByStatus("PENDING");
    }

    public int getApprovedCount() throws SQLException {
        return registrationDAO.getCountByStatus("APPROVED");
    }
}
