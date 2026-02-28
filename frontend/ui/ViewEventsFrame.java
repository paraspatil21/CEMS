package frontend.ui;

import backend.model.Student;
import javax.swing.*;

/**
 * ViewEventsFrame – replaced by StudentDashboardFrame tabs.
 * Kept for backward compatibility only.
 * 
 * @deprecated Use StudentDashboardFrame instead.
 */
@Deprecated
public class ViewEventsFrame extends JFrame {
    public ViewEventsFrame(Student student) {
        // Functionality moved to StudentDashboardFrame (Available Events tab)
        JOptionPane.showMessageDialog(null,
                "Please use the Student Dashboard to view and register for events.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
