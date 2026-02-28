package frontend.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Grid Panel (3x2)
        JPanel gridPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        gridPanel.setBorder(new EmptyBorder(10, 50, 20, 50));

        JButton manageEventsBtn = new JButton("Manage Events");
        JButton manageStudentsBtn = new JButton("Manage Students");
        JButton viewRegistrationsBtn = new JButton("View Registrations");
        JButton reportsBtn = new JButton("Reports");

        // Set default theme: Blue foreground text for primary buttons
        manageEventsBtn.setForeground(Color.BLUE);
        manageStudentsBtn.setForeground(Color.BLUE);
        viewRegistrationsBtn.setForeground(Color.BLUE);
        reportsBtn.setForeground(Color.BLUE);

        gridPanel.add(manageEventsBtn);
        gridPanel.add(manageStudentsBtn);
        gridPanel.add(viewRegistrationsBtn);
        gridPanel.add(reportsBtn);
        gridPanel.add(new JPanel()); // Filler
        gridPanel.add(new JPanel()); // Filler

        add(gridPanel, BorderLayout.CENTER);

        // Footer / Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.RED);
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(logoutButton);
        add(footerPanel, BorderLayout.SOUTH);

        // Listeners
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        manageEventsBtn.addActionListener(e -> new EventManagementFrame().setVisible(true));
        manageStudentsBtn.addActionListener(e -> new StudentManagementFrame().setVisible(true));
        viewRegistrationsBtn.addActionListener(e -> new RegistrationManagementFrame().setVisible(true));
        reportsBtn.addActionListener(e -> new ReportsFrame().setVisible(true));
    }
}
