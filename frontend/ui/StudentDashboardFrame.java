package frontend.ui;

import backend.model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentDashboardFrame extends JFrame {

    private final User currentUser;

    public StudentDashboardFrame(User user) {
        this.currentUser = user;
        setTitle("Student Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel titleLabel = new JLabel("Student Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setBorder(new EmptyBorder(10, 50, 20, 50));

        JButton viewEventsBtn = new JButton("View Events");
        JButton registerEventBtn = new JButton("Register For Event");
        JButton myRegistrationsBtn = new JButton("My Registrations");

        viewEventsBtn.setForeground(Color.BLUE);
        registerEventBtn.setForeground(Color.BLUE);
        myRegistrationsBtn.setForeground(Color.BLUE);

        gridPanel.add(viewEventsBtn);
        gridPanel.add(registerEventBtn);
        gridPanel.add(myRegistrationsBtn);
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

        viewEventsBtn.addActionListener(e -> new AvailableEventsFrame(currentUser).setVisible(true));
        registerEventBtn.addActionListener(e -> new AvailableEventsFrame(currentUser).setVisible(true));
        myRegistrationsBtn.addActionListener(e -> new MyRegistrationsFrame(currentUser).setVisible(true));
    }
}
