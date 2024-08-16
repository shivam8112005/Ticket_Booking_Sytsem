package model;

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

    private static String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static String user = "root";
    private static String password = "";

    private static final Scanner scanner = new Scanner(System.in);

    public DiscountPass() {
        System.out.println("Enter Discount Pass Details:");
        System.out.print("Enter pass name: ");
        this.name = scanner.nextLine();
        System.out.print("Enter discount percentage: ");
        this.discountPercentage = scanner.nextFloat();
        scanner.nextLine();
        addDiscountPassToDB(name, discountPercentage);
    }

    public DiscountPass(int discountPassID, String name, float discountPercentage) {
        this.discountPassID = discountPassID;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public DiscountPass(int a) {
    }

    public void addDiscountPassToDB(String name, float discountPercentage) {
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

    public static DiscountPass getDiscountPassFromDB(int discountPassID) {
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

    public void updateNameInDB(int discountPassID, String newName) {
        String query = "UPDATE DiscountPass SET PassName = ? WHERE DiscountPassID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setInt(2, discountPassID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Discount pass name updated successfully.");
                this.name = newName;
            } else {
                System.out.println("No discount pass found with ID: " + discountPassID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDiscountPercentageInDB(int discountPassID, float newDiscountPercentage) {
        String query = "UPDATE DiscountPass SET DiscountPercentage = ? WHERE DiscountPassID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setFloat(1, newDiscountPercentage);
            stmt.setInt(2, discountPassID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Discount pass percentage updated successfully.");
                this.discountPercentage = newDiscountPercentage;
            } else {
                System.out.println("No discount pass found with ID: " + discountPassID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiscountPassFromDB(int discountPassID) {
        String query = "DELETE FROM DiscountPass WHERE DiscountPassID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, discountPassID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Discount pass deleted successfully.");
            } else {
                System.out.println("No discount pass found with ID: " + discountPassID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllDiscountPasses() {
        String query = "SELECT * FROM DiscountPass";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println("Discount Passes:");
            while (rs.next()) {
                int id = rs.getInt("DiscountPassID");
                String name = rs.getString("PassName");
                float discountPercentage = rs.getFloat("DiscountPercentage");
                System.out.printf("ID: %d, Name: %s, Discount Percentage: %.2f%%\n", id, name, discountPercentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    // for testing purposes
    public static void main(String[] args) {
        DiscountPass dp1 = new DiscountPass();
        
        DiscountPass dp2 = DiscountPass.getDiscountPassFromDB(dp1.getDiscountPassID());
        if (dp2 != null) {
            System.out.println("Retrieved Discount Pass:");
            System.out.printf("ID: %d, Name: %s, Discount Percentage: %.2f%%\n", dp2.getDiscountPassID(), dp2.getName(),
            dp2.getDiscountPercentage());
        }
        
        dp2.updateNameInDB(dp2.getDiscountPassID(), "NewPassName");
        dp2.updateDiscountPercentageInDB(dp2.getDiscountPassID(), 15.5f);
        System.out.printf("ID: %d, Name: %s, Discount Percentage: %.2f%%\n", dp2.getDiscountPassID(), dp2.getName(),
        dp2.getDiscountPercentage());
        
        DiscountPass.printAllDiscountPasses();
        DiscountPass dp3 = new DiscountPass();
        DiscountPass dp4 = new DiscountPass();
        DiscountPass dp5 = new DiscountPass();

        // dp2.deleteDiscountPassFromDB(dp2.getDiscountPassID());

        DiscountPass.printAllDiscountPasses();
    }

}
