package frontend.ui;

import backend.model.Registration;
import backend.service.RegistrationService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RegistrationManagementFrame extends JFrame {

    private JTable regTable;
    private DefaultTableModel tableModel;
    private final RegistrationService regService = new RegistrationService();

    public RegistrationManagementFrame() {
        setTitle("Registration Management");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("View Registrations", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Student Name", "Event Name", "Status", "Date" };
        tableModel = new DefaultTableModel(columns, 0);
        regTable = new JTable(tableModel);
        add(new JScrollPane(regTable), BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton refreshBtn = new JButton("Refresh");
        JButton backBtn = new JButton("Back");

        approveBtn.setForeground(Color.BLUE);
        rejectBtn.setForeground(Color.RED);

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        backBtn.addActionListener(e -> dispose());
        refreshBtn.addActionListener(e -> refreshTable());
        approveBtn.addActionListener(e -> updateStatus("APPROVED"));
        rejectBtn.addActionListener(e -> updateStatus("REJECTED"));

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Registration> regs = regService.getAllRegistrations();
            tableModel.setRowCount(0);
            for (Registration r : regs) {
                tableModel.addRow(new Object[] {
                        r.getRegistrationId(),
                        r.getStudentName(),
                        r.getEventName(),
                        r.getApprovalStatus(),
                        r.getRegistrationDate()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String status) {
        int row = regTable.getSelectedRow();
        if (row == -1)
            return;
        int id = (int) tableModel.getValueAt(row, 0);
        try {
            regService.updateApprovalStatus(id, status);
            JOptionPane.showMessageDialog(this, "Status updated to " + status);
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
