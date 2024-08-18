package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;

public class DiscountPass {
    private int discountPassID;
    private String name;
    private float discountPercentage;

    private String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private String user = "root";
    private String password = "";

    private final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public DiscountPass() {
        System.out.println("Enter Discount Pass Details:");
        setName();
        System.out.print("Enter discount percentage: ");
        this.discountPercentage = scanner.nextFloat();
        scanner.nextLine();
        addDiscountPassToDB(name, discountPercentage);
    }

    // Parameterized constructor
    public DiscountPass(int discountPassID, String name, float discountPercentage) {
        this.discountPassID = discountPassID;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    // Constructor to access methods
    public DiscountPass(int a) {
    }

    // Method to set the name with uniqueness validation
    public void setName() {
        HashSet<String> existingNames = getAllDiscountPassNames();

        while (true) {
            System.out.print("Enter pass name: ");
            String input = scanner.nextLine();

            if (!existingNames.contains(input)) {
                this.name = input;
                break;
            } else {
                System.out.println("Discount pass name already exists. Please enter a different name.");
            }
        }
    }

    // Method to add the discount pass details to the database
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

    // Method to retrieve a DiscountPass object from the database using the
    // DiscountPassID
    public DiscountPass getDiscountPassFromDB(int discountPassID) {
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

    // Method to update the name in the database
    public void updateNameInDB(int discountPassID, String newName) {
        HashSet<String> existingNames = getAllDiscountPassNames();

        if (existingNames.contains(newName)) {
            System.out.println("Discount pass name already exists. Please enter a different name.");
            return;
        }

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

    // Method to update the discount percentage in the database
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

    // Method to delete a discount pass from the database by ID
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

    // Method to print all discount passes
    public void printAllDiscountPasses() {
        String query = "SELECT * FROM AllDiscountPass";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println("Discount Passes:");
            while (rs.next()) {
                int id = rs.getInt("DiscountPassID");
                String name = rs.getString("PassName");
                float discountPercentage = rs.getFloat("DiscountPercentage");
                System.out.println("ID: " + id + ", Name: " + name + ", Discount Percentage: "
                        + String.format("%.2f", discountPercentage) + "%");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all discount pass names from the database
    public HashSet<String> getAllDiscountPassNames() {
        String query = "SELECT PassName FROM DiscountPass";
        HashSet<String> nameSet = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("PassName");
                nameSet.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nameSet;
    }

    // Getters and Setters
    public int getDiscountPassID() {
        return discountPassID;
    }

    public String getName() {
        return name;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public String toString() {
        return "DiscountPass\ndiscountPassID: " + discountPassID + "\nname: " + name + "\ndiscountPercentage: "
                + discountPercentage;
    }

    // Main method for testing
    public static void main(String[] args) {
        DiscountPass dp1 = new DiscountPass(0);

        // DiscountPass dp2 = dp1.getDiscountPassFromDB(dp1.getDiscountPassID());
        // if (dp2 != null) {
        // System.out.println("Retrieved Discount Pass:");
        // System.out.println("ID: " + dp2.getDiscountPassID() +
        // ", Name: " + dp2.getName() +
        // ", Discount Percentage: " + String.format("%.2f",
        // dp2.getDiscountPercentage()) + "%");
        // }

        // dp2.updateNameInDB(dp2.getDiscountPassID(), "NewPassName");
        // dp2.updateDiscountPercentageInDB(dp2.getDiscountPassID(), 15.5f);
        // System.out.println("ID: " + dp2.getDiscountPassID() +
        // ", Name: " + dp2.getName() +
        // ", Discount Percentage: " + String.format("%.2f",
        // dp2.getDiscountPercentage()) + "%");

        dp1.printAllDiscountPasses();
        // DiscountPass dp2 = new DiscountPass();
        // DiscountPass dp3 = new DiscountPass();
        // DiscountPass dp4 = new DiscountPass();

        // dp2.deleteDiscountPassFromDB(dp2.getDiscountPassID());

        // dp1.printAllDiscountPasses();
    }
}
