package person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.regex.Pattern;

import DataStructure.InputValidator;

import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import menu.AdminMenu;

public class Admin implements Runnable{
    private Scanner scanner = new Scanner(System.in);

    private int adminId;
    private String username;
    private String password;
    HashSet<String> existingUsernames;
    InputValidator ip=new InputValidator();
    private final String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String dbUser = "root";
    private final String dbPassword = "";
    public void run(){
        this.existingUsernames=  getAllUsernames();
    }

    public Admin(Admin a) {
      
        while (true) {
            System.out.print("Enter username: ");
            this.username = scanner.next();
            scanner.nextLine(); // Consume newline

            if (a.existingUsernames.contains(this.username)) {
                System.out.println("Username already exists. Please choose another one.");
            } else {
                break;
            }
        }

        this.password = setValidPassword();
        this.adminId = saveToDB();
    }

    public Admin(int a) {
        // For methods that might require adminId directly
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        String n1=ip.encryptPassword(this.password);
     //   System.out.println(n1);
        String query = "SELECT id FROM Admin WHERE username = ? AND password = ?";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, this.username);
            pst.setString(2, n1);
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

    public int saveToDB() {
        String query = "INSERT INTO Admin (username, password) VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
String n1=ip.encryptPassword(this.password);
            pst.setString(1, this.username);
            pst.setString(2, n1);
            pst.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int Id = generatedKeys.getInt(1);
                System.out.println("Admin added successfully with ID: " + Id);
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

    public void updateName() {
       

        while (true) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            if (existingUsernames.contains(newUsername)) {
                System.out.println("Username already exists. Please choose another one.");
            } else {
                String query = "UPDATE Admin SET username = ? WHERE id = ?";
                try {
                    Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                    PreparedStatement pst = connection.prepareStatement(query);

                    pst.setString(1, newUsername);
                    pst.setInt(2, this.getAdminId());

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Username updated successfully.");
                        break; // Exit loop after successful update
                    } else {
                        System.out.println("No record found with ID: " + this.getAdminId());
                        break; // Exit loop on error
                    }

                } catch (SQLException e) {
                    System.out.println("Error updating username: " + e.getMessage());
                    break; // Exit loop on error
                }
            }
        }
    }

    public void updatePassword(int id) {
        String newPassword = setValidPassword();
        String n1=ip.encryptPassword(newPassword);
        String query = "UPDATE Admin SET password = ? WHERE id = ?";

        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, n1);
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

    private HashSet<String> getAllUsernames() {
        HashSet<String> usernames = new HashSet<>();
        String query = "SELECT username FROM Admin";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving usernames: " + e.getMessage());
        }
        return usernames;
    }
}
