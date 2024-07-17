import java.sql.*;

public class DatabaseUtil {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_system", "root", "");
        System.out.println(con != null ? "success" : "error");
    }
}
