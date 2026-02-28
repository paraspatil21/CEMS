import backend.util.DBConnection;

public class Test {
    public static void main(String[] args) throws Exception {
        DBConnection.getConnection();
        System.out.println("SUCCESS");
    }
}
