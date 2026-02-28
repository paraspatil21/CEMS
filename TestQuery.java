import backend.util.DBConnection;
import java.sql.*;

public class TestQuery {
    public static void main(String[] args) throws Exception {
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        System.out.println("Users in db:");
        while (rs.next()) {
            System.out.println(rs.getString("username") + " | " + rs.getString("role"));
        }
        System.out.println("Done.");
    }
}
