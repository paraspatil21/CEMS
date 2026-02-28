package frontend.ui;

import backend.model.Event;
import backend.model.Student;
import backend.model.User;
import backend.dao.StudentDAO;
import backend.service.EventService;
import backend.service.RegistrationService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AvailableEventsFrame extends JFrame {

    private JTable eventTable;
    private DefaultTableModel tableModel;
    private final EventService eventService = new EventService();
    private final RegistrationService regService = new RegistrationService();
    private Student currentStudent;

    public AvailableEventsFrame(User user) {
        // Load student profile
        try {
            this.currentStudent = new StudentDAO().getStudentByUserId(user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle("Available Events");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("Available Events", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Date", "Venue", "Slots", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton registerBtn = new JButton("Register Now");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        registerBtn.setForeground(Color.BLUE);

        btnPanel.add(registerBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        backBtn.addActionListener(e -> dispose());
        refreshBtn.addActionListener(e -> refreshTable());
        registerBtn.addActionListener(e -> handleRegister());

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Event> events = eventService.getAllEvents();
            tableModel.setRowCount(0);
            for (Event e : events) {
                int slots = e.getMaxParticipants() - e.getRegisteredCount();
                tableModel.addRow(new Object[] {
                        e.getEventId(), e.getEventName(), e.getEventDate(), e.getVenue(), slots, e.getStatus()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRegister() {
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(this, "Student profile not found.");
            return;
        }
        int row = eventTable.getSelectedRow();
        if (row == -1)
            return;
        int eventId = (int) tableModel.getValueAt(row, 0);
        int slots = (int) tableModel.getValueAt(row, 4);

        if (slots <= 0) {
            JOptionPane.showMessageDialog(this, "No slots available.");
            return;
        }

        try {
            regService.registerForEvent(currentStudent.getStudentId(), eventId);
            JOptionPane.showMessageDialog(this, "Successfully Registered!");
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
