package frontend.ui;

import backend.model.Registration;
import backend.model.Student;
import backend.model.User;
import backend.dao.StudentDAO;
import backend.service.RegistrationService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MyRegistrationsFrame extends JFrame {

    private JTable regTable;
    private DefaultTableModel tableModel;
    private final RegistrationService regService = new RegistrationService();
    private Student currentStudent;

    public MyRegistrationsFrame(User user) {
        try {
            this.currentStudent = new StudentDAO().getStudentByUserId(user.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle("My Registrations");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("My Registrations", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Event Name", "Status", "Date" };
        tableModel = new DefaultTableModel(columns, 0);
        regTable = new JTable(tableModel);
        add(new JScrollPane(regTable), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton cancelBtn = new JButton("Cancel Registration");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        cancelBtn.setForeground(Color.RED);

        btnPanel.add(cancelBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        backBtn.addActionListener(e -> dispose());
        refreshBtn.addActionListener(e -> refreshTable());
        cancelBtn.addActionListener(e -> handleCancel());

        refreshTable();
    }

    private void refreshTable() {
        if (currentStudent == null)
            return;
        try {
            List<Registration> regs = regService.getRegistrationsByStudent(currentStudent.getStudentId());
            tableModel.setRowCount(0);
            for (Registration r : regs) {
                tableModel.addRow(new Object[] {
                        r.getRegistrationId(), r.getEventName(), r.getApprovalStatus(), r.getRegistrationDate()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleCancel() {
        if (currentStudent == null)
            return;
        int row = regTable.getSelectedRow();
        if (row == -1)
            return;
        int regId = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                regService.cancelRegistration(regId, currentStudent.getStudentId());
                JOptionPane.showMessageDialog(this, "Registration Cancelled");
                refreshTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}
