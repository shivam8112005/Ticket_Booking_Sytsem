package person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import menu.AdminMenu;

public class Admin {
    private Scanner scanner = new Scanner(System.in);

    private int adminId;
    private String username;
    private String password;

    private final String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String dbUser = "root";
    private final String dbPassword = "";

    public Admin() {
        System.out.print("Enter username: ");
        this.username = scanner.next();
        this.password = setValidPassword();
        this.adminId = saveToDB(username, this.password);
        AdminMenu am = new AdminMenu(this);
        am.adminMenu();// open menu after u have register
    }

    public Admin(int a) {
        // For methods that might require adminId directly
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;

        String query = "SELECT id FROM Admin WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, this.username);
            pst.setString(2, this.password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                this.adminId = rs.getInt("id");
                AdminMenu am = new AdminMenu(this);
                am.adminMenu(); // open menu after login
            } else {
                System.out.println("Invalid username or password.");
                // return to sign-up menu or handle error
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    public int saveToDB(String username, String password) {
        String query = "INSERT INTO Admin (username, password) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int Id = generatedKeys.getInt(1);
                System.out.println("Admin added successfully with ID: " + this.adminId);
                return Id;
            } else {
                System.out.println("Failed to obtain admin ID.");
                return -1; // using -1 to indicate error
            }
        } catch (SQLException e) {
            System.out.println("Error adding admin: " + e.getMessage());
            return -1; // using -1 to indicate error
        }
    }

    public String setValidPassword() {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        String pass;
        while (true) {
            System.out.print("Enter password: ");
            pass = scanner.nextLine();
            if (Pattern.matches(regex, pass)) {
                return pass;
            } else {
                System.out.println(
                        "Password must be at least 8 characters long, contain at least 1 digit, 1 lowercase letter, and 1 uppercase letter.");
                System.out.println();
            }
        }
    }

    public void updateUsername(int id) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        String query = "UPDATE Admin SET username = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, newUsername);
            pst.setInt(2, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username updated successfully.");
            } else {
                System.out.println("No record found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error updating username: " + e.getMessage());
        }
    }

    public void updatePassword(int id) {
        String newPassword = setValidPassword();
        String query = "UPDATE Admin SET password = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, newPassword);
            pst.setInt(2, id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("No record found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

    public int getAdminId() {
        return adminId;
    }

    public String getName() {
        return username;
    }

}
