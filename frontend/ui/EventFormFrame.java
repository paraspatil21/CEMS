package frontend.ui;

import backend.model.Event;
import backend.service.EventService;
import backend.util.ValidationUtil;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.SQLException;

/**
 * Event Form Frame – handles both Add and Update operations.
 * Pass null event for Add; pass an existing Event for Update.
 */
public class EventFormFrame extends JFrame {

    private JTextField nameField, typeField, dateField, timeField, venueField,
            organizerField, maxField, deadlineField;
    private JComboBox<String> statusCombo;
    private final Event existingEvent;
    private final Runnable onSaveCallback;
    private final EventService eventService = new EventService();

    public EventFormFrame(Event existingEvent, Runnable onSaveCallback) {
        this.existingEvent = existingEvent;
        this.onSaveCallback = onSaveCallback;

        boolean isUpdate = (existingEvent != null);
        setTitle(isUpdate ? "Update Event" : "Add New Event");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ── Title ──────────────────────────────────────────────
        JLabel titleLabel = new JLabel(isUpdate ? "Update Event" : "Add New Event", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 8, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ── Form ───────────────────────────────────────────────
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 6, 6));
        formPanel.setBorder(BorderFactory.createEmptyBorder(6, 40, 8, 40));

        nameField = new JTextField();
        typeField = new JTextField();
        dateField = new JTextField("YYYY-MM-DD");
        timeField = new JTextField("HH:MM:SS");
        venueField = new JTextField();
        organizerField = new JTextField();
        maxField = new JTextField();
        deadlineField = new JTextField("YYYY-MM-DD");
        statusCombo = new JComboBox<>(new String[] { "UPCOMING", "ONGOING", "COMPLETED", "CANCELLED" });

        formPanel.add(new JLabel("Event Name: *"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Event Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Date: *"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Time: *"));
        formPanel.add(timeField);
        formPanel.add(new JLabel("Venue: *"));
        formPanel.add(venueField);
        formPanel.add(new JLabel("Organizer:"));
        formPanel.add(organizerField);
        formPanel.add(new JLabel("Max Participants: *"));
        formPanel.add(maxField);
        formPanel.add(new JLabel("Deadline:"));
        formPanel.add(deadlineField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusCombo);
        formPanel.add(new JLabel("* Required", SwingConstants.RIGHT));
        formPanel.add(new JLabel(""));

        add(formPanel, BorderLayout.CENTER);

        // ── Buttons ────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton saveBtn = new JButton(isUpdate ? "Update Event" : "Save Event");
        JButton cancelBtn = new JButton("Cancel");
        saveBtn.setForeground(Color.BLUE);
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ── Populate for update ────────────────────────────────
        if (isUpdate) {
            populateFields(existingEvent);
        }

        saveBtn.addActionListener(e -> saveEvent(isUpdate));
        cancelBtn.addActionListener(e -> dispose());
    }

    private void populateFields(Event ev) {
        nameField.setText(ev.getEventName());
        typeField.setText(ev.getEventType() != null ? ev.getEventType() : "");
        dateField.setText(ev.getEventDate().toString());
        timeField.setText(ev.getEventTime().toString());
        venueField.setText(ev.getVenue());
        organizerField.setText(ev.getOrganizer() != null ? ev.getOrganizer() : "");
        maxField.setText(String.valueOf(ev.getMaxParticipants()));
        deadlineField.setText(ev.getDeadline() != null ? ev.getDeadline().toString() : "");
        statusCombo.setSelectedItem(ev.getStatus() != null ? ev.getStatus() : "UPCOMING");
    }

    private void saveEvent(boolean isUpdate) {
        String name = nameField.getText().trim();
        String dateStr = dateField.getText().trim();
        String timeStr = timeField.getText().trim();
        String venue = venueField.getText().trim();
        String maxStr = maxField.getText().trim();

        if (ValidationUtil.isEmpty(name) || ValidationUtil.isEmpty(dateStr)
                || ValidationUtil.isEmpty(timeStr) || ValidationUtil.isEmpty(venue)
                || ValidationUtil.isEmpty(maxStr)) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required (*) fields.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!ValidationUtil.isNumber(maxStr)) {
            JOptionPane.showMessageDialog(this,
                    "Max Participants must be a valid number.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Event event = (isUpdate) ? existingEvent : new Event();
            event.setEventName(name);
            event.setEventType(typeField.getText().trim());
            event.setEventDate(Date.valueOf(dateStr));
            event.setEventTime(Time.valueOf(timeStr));
            event.setVenue(venue);
            event.setOrganizer(organizerField.getText().trim());
            event.setMaxParticipants(Integer.parseInt(maxStr));
            String dl = deadlineField.getText().trim();
            event.setDeadline(dl.isEmpty() || dl.equals("YYYY-MM-DD") ? null : Date.valueOf(dl));
            event.setStatus((String) statusCombo.getSelectedItem());

            if (isUpdate) {
                eventService.updateEvent(event);
                JOptionPane.showMessageDialog(this, "Event updated successfully.");
            } else {
                eventService.addEvent(event);
                JOptionPane.showMessageDialog(this, "Event added successfully.");
            }

            if (onSaveCallback != null) {
                onSaveCallback.run();
            }
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date/time format. Use YYYY-MM-DD and HH:MM:SS.", "Format Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
