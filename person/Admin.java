package person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin {
    private Scanner scanner = new Scanner(System.in);

    private int adminId;
    private String username;
    private String phoneNumber;
    private String password;

    private final String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String dbUser = "root";
    private final String dbPassword = "";

    public Admin() {
        System.out.print("Enter username: ");
        this.username = scanner.nextLine();
        this.phoneNumber = setValidPhoneNumber();
        this.password = setValidPassword();
        saveToDB(username, this.phoneNumber, this.password);
    }

    public Admin(int a) {
        // For methods that might require adminId directly
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;

        String query = "SELECT id, phoneNumber FROM Admin WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, this.username);
            pst.setString(2, this.password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                this.adminId = rs.getInt("id");
                this.phoneNumber = rs.getString("phoneNumber");
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    public void saveToDB(String username, String phoneNumber, String password) {
        String query = "INSERT INTO Admin (username, password, phoneNumber) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, phoneNumber);
            pst.executeUpdate();
            System.out.println("Admin added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding admin: " + e.getMessage());
        }
    }

    public String setValidPhoneNumber() {
        String phone;
        while (true) {
            System.out.print("Enter phone number: ");
            phone = scanner.nextLine();

            String phonePattern = "^[0-9]{10}$";

            if (Pattern.matches(phonePattern, phone)) {
                return phone;
            } else {
                System.out.println("Invalid phone number!");
                System.out.println();
            }
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
            pst.executeUpdate();
            System.out.println("Username updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating username: " + e.getMessage());
        }
    }

    public void updatePhoneNumber(int id) {
        String newPhoneNumber = setValidPhoneNumber();
        String query = "UPDATE Admin SET phoneNumber = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, newPhoneNumber);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("Phone number updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating phone number: " + e.getMessage());
        }
    }

    public void updatePassword(int id) {
        String newPassword = setValidPassword();
        String query = "UPDATE Admin SET password = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                PreparedStatement pst = connection.prepareStatement(query)) {

            pst.setString(1, newPassword);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("Password updated successfully.");
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
