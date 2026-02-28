package frontend.ui;

import backend.model.User;
import backend.model.Student;
import backend.service.AuthService;
import backend.util.ValidationUtil;
import javax.swing.*;
import java.awt.*;

/**
 * Student Registration Form – creates a user account and student profile in one
 * transaction.
 */
public class RegistrationFormFrame extends JFrame {

    private JTextField usernameField, nameField, rollField, deptField, yearField, contactField, emailField;
    private JPasswordField passwordField;
    private final AuthService authService = new AuthService();

    public RegistrationFormFrame() {
        setTitle("Student Registration");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ── Title ──────────────────────────────────────────────
        JLabel titleLabel = new JLabel("Student Registration", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 8, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ── Form ───────────────────────────────────────────────
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 6, 6));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        nameField = new JTextField();
        rollField = new JTextField();
        deptField = new JTextField();
        yearField = new JTextField();
        contactField = new JTextField();
        emailField = new JTextField();

        formPanel.add(new JLabel("Username: *"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password: *"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Full Name: *"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll Number: *"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(deptField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("* Required", SwingConstants.RIGHT));
        formPanel.add(new JLabel(""));

        add(formPanel, BorderLayout.CENTER);

        // ── Buttons ────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back to Login");
        btnPanel.add(registerBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ── Listeners ──────────────────────────────────────────
        registerBtn.addActionListener(e -> handleRegistration());

        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();

        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)
                || ValidationUtil.isEmpty(name) || ValidationUtil.isEmpty(roll)) {
            JOptionPane.showMessageDialog(this,
                    "Fields marked with * are required.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setRole("STUDENT");

            Student s = new Student();
            s.setName(name);
            s.setRollNumber(roll);
            s.setDepartment(deptField.getText().trim());
            s.setYear(yearField.getText().trim());
            s.setContact(contactField.getText().trim());
            s.setEmail(emailField.getText().trim());

            if (authService.registerStudent(u, s)) {
                JOptionPane.showMessageDialog(this,
                        "Registration successful! You can now login.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Registration failed. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
