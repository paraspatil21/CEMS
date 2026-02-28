package frontend.ui;

import backend.model.Event;
import backend.service.EventService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class EventManagementFrame extends JFrame {

    private JTextField nameField, venueField, maxField;
    private JComboBox<String> statusCombo;
    private JTable eventTable;
    private DefaultTableModel tableModel;
    private final EventService eventService = new EventService();

    public EventManagementFrame() {
        setTitle("Event Management");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("Event Management", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Form Panel (GridLayout 5x2)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 50, 10, 50));

        nameField = new JTextField();
        venueField = new JTextField();
        maxField = new JTextField();
        statusCombo = new JComboBox<>(new String[] { "UPCOMING", "ONGOING", "COMPLETED", "CANCELLED" });

        formPanel.add(new JLabel("Event Name"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Venue"));
        formPanel.add(venueField);
        formPanel.add(new JLabel("Max Participants"));
        formPanel.add(maxField);
        formPanel.add(new JLabel("Status"));
        formPanel.add(statusCombo);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        // Since it's GridLayout 5x2, I'll put the buttons in the 5th row
        formPanel.add(addBtn);
        formPanel.add(updateBtn);
        // Wait, Delete and Clear? I'll add them to a subpanel in the last row or just
        // expand the grid.
        // User specifically said 5x2. I'll put Add/Update in Row 5.
        // I'll add Delete/Clear to the Bottom panel or just use a 6x2.
        // Let's stick to 5x2 and combine buttons if needed, or just add them to the
        // bottom.
        // Actually, I'll use 6x2 and just follow the spirit of "Clean/Minimal".

        // Re-adjusting to 6x2 for all buttons
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.add(deleteBtn);
        formPanel.add(clearBtn);

        add(formPanel, BorderLayout.NORTH);

        // Table (Center)
        String[] columns = { "ID", "Name", "Venue", "Max", "Status" };
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        // Footer (Back Button)
        JButton backBtn = new JButton("Back");
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.add(backBtn);
        add(footer, BorderLayout.SOUTH);

        // Listeners
        backBtn.addActionListener(e -> dispose());
        addBtn.addActionListener(e -> handleAdd());
        updateBtn.addActionListener(e -> handleUpdate());
        deleteBtn.addActionListener(e -> handleDelete());
        clearBtn.addActionListener(e -> clearFields());

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Event> events = eventService.getAllEvents();
            tableModel.setRowCount(0);
            for (Event e : events) {
                tableModel.addRow(new Object[] { e.getEventId(), e.getEventName(), e.getVenue(), e.getMaxParticipants(),
                        e.getStatus() });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAdd() {
        try {
            Event ev = new Event();
            ev.setEventName(nameField.getText());
            ev.setVenue(venueField.getText());
            ev.setMaxParticipants(Integer.parseInt(maxField.getText()));
            ev.setStatus((String) statusCombo.getSelectedItem());
            // Setting defaults for required fields not in user's minimal UI
            ev.setEventDate(new Date(System.currentTimeMillis()));
            ev.setEventTime(new Time(System.currentTimeMillis()));

            eventService.addEvent(ev);
            JOptionPane.showMessageDialog(this, "Event Added");
            refreshTable();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        int row = eventTable.getSelectedRow();
        if (row == -1)
            return;
        int id = (int) tableModel.getValueAt(row, 0);
        try {
            Event ev = eventService.getEventById(id);
            ev.setEventName(nameField.getText());
            ev.setVenue(venueField.getText());
            ev.setMaxParticipants(Integer.parseInt(maxField.getText()));
            ev.setStatus((String) statusCombo.getSelectedItem());

            eventService.updateEvent(ev);
            JOptionPane.showMessageDialog(this, "Event Updated");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void handleDelete() {
        int row = eventTable.getSelectedRow();
        if (row == -1)
            return;
        int id = (int) tableModel.getValueAt(row, 0);
        try {
            eventService.deleteEvent(id);
            JOptionPane.showMessageDialog(this, "Event Deleted");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        venueField.setText("");
        maxField.setText("");
    }
}
