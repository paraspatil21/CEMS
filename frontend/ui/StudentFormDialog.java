package frontend.ui;

import backend.model.Student;
import backend.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StudentFormDialog extends JDialog {
    private final StudentService studentService;
    private final Student student;
    private final JTextField nameField;
    private final JTextField rollField;
    private final JTextField deptField;

    public StudentFormDialog(JFrame parent, Student student, StudentService studentService) {
        super(parent, student == null ? "Add Student" : "Edit Student", true);
        this.studentService = studentService;
        this.student = student == null ? new Student() : student;

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = new JTextField(this.student.getName());
        rollField = new JTextField(this.student.getRollNumber());
        deptField = new JTextField(this.student.getDepartment());
        // Do not allow editing roll number if editing existing to avoid issues with
        // username, but it's okay for now.

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll Number:"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(deptField);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveStudent());
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void saveStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String dept = deptField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        student.setName(name);
        student.setRollNumber(roll);
        student.setDepartment(dept);

        // Ensure defaults for non-null columns not collected
        if (student.getYear() == null)
            student.setYear("1st Year");
        if (student.getContact() == null)
            student.setContact("");
        if (student.getEmail() == null)
            student.setEmail("");

        try {
            studentService.saveStudent(student);
            JOptionPane.showMessageDialog(this, "Student saved successfully!");
            dispose();
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving student: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
