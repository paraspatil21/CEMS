import frontend.ui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point for College Event Management System.
 */
public class Main {
    public static void main(String[] args) {
        // Use default system look and feel (no custom or FlatLaf theme)
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
