import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DiscountPass {
    private int discountPassID;
    private String name;
    private float discountPercentage;

    private static final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public DiscountPass() {
        System.out.println("Enter Discount Pass Details:");
        System.out.print("Enter pass name: ");
        this.name = scanner.nextLine();
        System.out.print("Enter discount percentage: ");
        this.discountPercentage = scanner.nextFloat();
        addDiscountPassToDB(name, discountPercentage);
    }

    // Parameterized constructor
    public DiscountPass(int discountPassID, String name, float discountPercentage) {
        this.discountPassID = discountPassID;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    // Method to add the discount pass details to the database
    public void addDiscountPassToDB(String name, float discountPercentage) {
        String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
        String user = "root";
        String password = "";

        String query = "INSERT INTO DiscountPass (PassName, DiscountPercentage) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setFloat(2, discountPercentage);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.discountPassID = rs.getInt(1);
                System.out.println("Discount pass added with ID: " + this.discountPassID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a DiscountPass object from the database using the
    // DiscountPassID
    public static DiscountPass getDiscountPassFromDB(int discountPassID) {
        String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM DiscountPass WHERE DiscountPassID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, discountPassID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("PassName");
                float discountPercentage = rs.getFloat("DiscountPercentage");
                return new DiscountPass(discountPassID, name, discountPercentage);
            } else {
                System.out.println("No discount pass found with ID: " + discountPassID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountPassID() {
        return discountPassID;
    }

    public String getName() {
        return name;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }
}
