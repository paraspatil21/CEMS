package frontend.ui;

import backend.model.Student;
import backend.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentManagementFrame extends JFrame {
    private final StudentService studentService;
    private final DefaultTableModel tableModel;
    private final JTable studentTable;
    private final JTextField searchField;

    public StudentManagementFrame() {
        this.studentService = new StudentService();
        setTitle("Student Management");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("STUDENT MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        // Top Toolbar
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");
        JButton btnAdd = new JButton("➕ Add Student");
        JButton btnEdit = new JButton("✏ Edit Student");
        JButton btnDelete = new JButton("❌ Delete Student");
        JButton btnRefresh = new JButton("🔄 Refresh");

        toolbarPanel.add(new JLabel("Search:"));
        toolbarPanel.add(searchField);
        toolbarPanel.add(searchBtn);
        toolbarPanel.add(Box.createHorizontalStrut(20));
        toolbarPanel.add(btnAdd);
        toolbarPanel.add(btnEdit);
        toolbarPanel.add(btnDelete);
        toolbarPanel.add(btnRefresh);

        headerPanel.add(toolbarPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // Center Table
        String[] columns = { "ID", "Name", "Roll No", "Department" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < studentTable.getColumnCount(); i++) {
            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        searchBtn.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        btnRefresh.addActionListener(e -> loadStudents());
        btnAdd.addActionListener(e -> showForm(null));
        btnEdit.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to edit.");
                return;
            }
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String roll = (String) tableModel.getValueAt(selectedRow, 2);
            String dept = (String) tableModel.getValueAt(selectedRow, 3);

            Student s = new Student();
            s.setStudentId(studentId);
            s.setName(name);
            s.setRollNumber(roll);
            s.setDepartment(dept);
            showForm(s);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to delete.");
                return;
            }
            int studentId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean success = studentService.deleteStudent(studentId);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                        loadStudents();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete student.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadStudents();
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        try {
            List<Student> students;
            if (query.isEmpty()) {
                students = studentService.getAllStudents();
            } else {
                students = studentService.searchStudents(query);
            }
            populateTable(students);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching: " + ex.getMessage());
        }
    }

    private void loadStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            populateTable(students);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Data: " + ex.getMessage());
        }
    }

    private void populateTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[] {
                    s.getStudentId(),
                    s.getName(),
                    s.getRollNumber(),
                    s.getDepartment()
            });
        }
    }

    private void showForm(Student s) {
        new StudentFormDialog(this, s, studentService).setVisible(true);
        loadStudents();
    }
}
