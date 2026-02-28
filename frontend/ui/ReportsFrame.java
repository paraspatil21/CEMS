package frontend.ui;

import backend.service.ReportsService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportsFrame extends JFrame {
    private final ReportsService reportsService;
    private final DefaultTableModel tableModel;
    private final JTable reportTable;
    private final JLabel lblTotalStudents;
    private final JLabel lblTotalEvents;
    private final JLabel lblTotalRegistrations;

    public ReportsFrame() {
        this.reportsService = new ReportsService();
        setTitle("System Reports");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header and Summary
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("SYSTEM REPORTS", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        summaryPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        lblTotalStudents = createSummaryLabel("Total Students: 0");
        lblTotalEvents = createSummaryLabel("Total Events: 0");
        lblTotalRegistrations = createSummaryLabel("Total Registrations: 0");

        summaryPanel.add(lblTotalStudents);
        summaryPanel.add(lblTotalEvents);
        summaryPanel.add(lblTotalRegistrations);

        topPanel.add(summaryPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Center Table
        String[] columns = { "Event Name", "Total Registrations" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);
        reportTable.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < reportTable.getColumnCount(); i++) {
            reportTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(reportTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event-wise Registration Report"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Toolbar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnRefresh = new JButton("🔄 Refresh");
        JButton btnExport = new JButton("📄 Export to CSV");
        JButton btnBack = new JButton("🔙 Back");

        btnRefresh.addActionListener(e -> loadData());
        btnExport.addActionListener(e -> exportToCSV());
        btnBack.addActionListener(e -> dispose());

        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnExport);
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();
    }

    private JLabel createSummaryLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        label.setOpaque(true);
        label.setBackground(new Color(230, 240, 255));
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        label.setPreferredSize(new Dimension(200, 60));
        return label;
    }

    private void loadData() {
        try {
            int ts = reportsService.getTotalStudents();
            int te = reportsService.getTotalEvents();
            int tr = reportsService.getTotalRegistrations();

            lblTotalStudents.setText("Total Students: " + ts);
            lblTotalEvents.setText("Total Events: " + te);
            lblTotalRegistrations.setText("Total Registrations: " + tr);

            List<Object[]> eventReport = reportsService.getEventWiseReport();
            tableModel.setRowCount(0);
            for (Object[] row : eventReport) {
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading report data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as CSV");
        fileChooser.setSelectedFile(new java.io.File("reports.csv"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Header
                writer.write("Event Name,Total Registrations\n");
                // Data
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String eventName = tableModel.getValueAt(i, 0).toString();
                    String rCount = tableModel.getValueAt(i, 1).toString();

                    // escape comma
                    if (eventName.contains(",")) {
                        eventName = "\"" + eventName + "\"";
                    }
                    writer.write(eventName + "," + rCount + "\n");
                }
                JOptionPane.showMessageDialog(this, "Report exported successfully to\n" + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error writing to CSV.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
