package frontend.ui;

import backend.model.User;
import backend.service.AuthService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("CEMS - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("College Event Management System", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        formPanel.setBorder(new EmptyBorder(20, 60, 20, 60));
        formPanel.setBackground(Color.WHITE);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleCombo = new JComboBox<>(new String[] { "STUDENT", "ADMIN" });

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleCombo);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.BLUE);
        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(Color.RED);

        formPanel.add(loginButton);
        formPanel.add(exitButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Status Label for Errors
        JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        loginButton.addActionListener(e -> handleLogin(statusLabel));
        exitButton.addActionListener(e -> System.exit(0));

        // Allow register link or similar if needed, but per request we are keeping it
        // minimalist.
        // If the user wants to register, we should probably have a button or link.
        // Let's add a small link at the bottom or another button.
        // Actually, the request says "Functional-only UI" and lists specific buttons.
        // I will stick to the listed buttons: Login, Exit.
    }

    private void handleLogin(JLabel statusLabel) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username and password required");
            return;
        }

        try {
            User user = authService.login(username, password);
            if (user != null) {
                if (!user.getRole().equalsIgnoreCase(selectedRole)) {
                    statusLabel.setText("Invalid role selected");
                    return;
                }

                statusLabel.setText(" ");
                if ("ADMIN".equals(user.getRole())) {
                    new AdminDashboardFrame().setVisible(true);
                } else {
                    new StudentDashboardFrame(user).setVisible(true);
                }
                dispose();
            } else {
                statusLabel.setText("Invalid credentials");
            }
        } catch (SQLException ex) {
            statusLabel.setText("Database error");
            ex.printStackTrace();
        }
    }
}
