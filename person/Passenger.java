package person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import model.DiscountPass;

public class Passenger {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private Date dob;
    private int discountPassId;
    private int associatedWith; // CustomerID

    private final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String USER = "root";
    private final String PASSWORD = "";

    // No-parameter constructor
    public Passenger(int customerID) {
        Scanner scanner = new Scanner(System.in);

        HashSet<String> phoneNumberSet = getAllPhoneNumbers();
        HashSet<String> emailSet = getAllEmails();

        System.out.print("Enter Name: ");
        this.name = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        this.phoneNumber = scanner.nextLine();
        while (!isValidPhoneNumber(this.phoneNumber) || phoneNumberSet.contains(this.phoneNumber)) {
            System.out.println("Invalid or duplicate phone number. Please re-enter the phone number:");
            this.phoneNumber = scanner.nextLine();
        }

        System.out.print("Enter Email: ");
        this.email = scanner.nextLine();
        while (!isValidEmail(this.email) || emailSet.contains(this.email)) {
            System.out.println("Email already exists. Please re-enter the email:");
            this.email = scanner.nextLine();
        }

        System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine();
        this.dob = java.sql.Date.valueOf(dobInput); // Converting String to Date

        DiscountPass dp = new DiscountPass(0);
        HashSet<Integer> hs = dp.getAllDiscountPassIDs();

        while (true) {
            dp.printAllDiscountPasses();
            System.out.print("Enter Discount Pass ID: ");
            this.discountPassId = scanner.nextInt();

            if (hs.contains(this.discountPassId)) {
                System.out.println("Discount Pass set!");
                break;
            } else {
                System.out.println("Invalid Discount Pass ID. Please try again.");
            }
        }

        this.associatedWith = customerID;

        // Call the method to add the passenger to the database
        addPassengerToDB(this.name, this.phoneNumber, this.email, this.dob, this.discountPassId, this.associatedWith);
    }

    private boolean isValidEmail(String email) {

        return !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    }

    // Parameterized constructor
    public Passenger(int id, String name, String phoneNumber, String email, Date dob, int discountPassId,
            int associatedWith) {
        Scanner scanner = new Scanner(System.in);

        HashSet<String> phoneNumberSet = getAllPhoneNumbers();
        HashSet<String> emailSet = getAllEmails();
        this.id = id;
        this.name = name;
        System.out.print("Enter Phone Number: ");
        this.phoneNumber = phoneNumber;
        while (!isValidPhoneNumber(this.phoneNumber) || phoneNumberSet.contains(this.phoneNumber)) {
            System.out.println("Invalid or duplicate phone number. Please re-enter the phone number:");
            this.phoneNumber = scanner.nextLine();
        }

        System.out.print("Enter Email: ");
        this.email = scanner.nextLine();
        while (!isValidEmail(this.email) || emailSet.contains(this.email)) {
            System.out.println("Email already exists. Please re-enter the email:");
            this.email = scanner.nextLine();
        }
        this.dob = dob;
        this.discountPassId = discountPassId;
        this.associatedWith = associatedWith;
    }

    public Passenger(char c) {
        // A constructor that is only to access methods
    }

    // Method to validate the phone number (example validation for 10-digit numbers)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    // Method to add a passenger to the database
    public void addPassengerToDB(String name, String phoneNumber, String email, Date dob, int discountPassId,
            int associatedWith) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO Passenger (PassengerName, PassengerNumber, PassengerEmail, PassengerDOB, DiscountPassID, AssociatedWith) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, new java.sql.Date(dob.getTime()));
            preparedStatement.setInt(5, discountPassId);
            preparedStatement.setInt(6, associatedWith);

            preparedStatement.executeUpdate();
            System.out.println("Passenger added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Passenger getPassengerFromDB(int passengerId) {
        Passenger passenger = null;

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM Passenger WHERE PassengerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, passengerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("PassengerID");
                String name = resultSet.getString("PassengerName");
                String phoneNumber = resultSet.getString("PassengerNumber");
                String email = resultSet.getString("PassengerEmail");
                Date dob = resultSet.getDate("PassengerDOB");
                int discountPassId = resultSet.getInt("DiscountPassID");
                int associatedWith = resultSet.getInt("AssociatedWith");

                passenger = new Passenger(id, name, phoneNumber, email, dob, discountPassId, associatedWith);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passenger;
    }

    // Method to get all phone numbers from the database
    public HashSet<String> getAllPhoneNumbers() {
        HashSet<String> phoneNumberSet = new HashSet<>();
        String sql = "SELECT PassengerNumber FROM Passenger";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                phoneNumberSet.add(resultSet.getString("PassengerNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phoneNumberSet;
    }

    // Method to get all emails from the database
    public HashSet<String> getAllEmails() {
        HashSet<String> emailSet = new HashSet<>();
        String sql = "SELECT PassengerEmail FROM Passenger";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                emailSet.add(resultSet.getString("PassengerEmail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailSet;
    }

    // Method to update the name
    public void updateName(int passengerId, String newName) {
        String sql = "UPDATE Passenger SET PassengerName = ? WHERE PassengerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, passengerId);
            preparedStatement.executeUpdate();

            System.out.println("Name updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the phone number and check uniqueness
    public void updatePhoneNumber(int passengerId, String newPhoneNumber) {
        HashSet<String> phoneNumberSet = getAllPhoneNumbers();
        if (!isValidPhoneNumber(newPhoneNumber) || phoneNumberSet.contains(newPhoneNumber)) {
            System.out.println("Invalid or duplicate phone number. Update failed.");
            return;
        }

        String sql = "UPDATE Passenger SET PassengerNumber = ? WHERE PassengerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPhoneNumber);
            preparedStatement.setInt(2, passengerId);
            preparedStatement.executeUpdate();

            System.out.println("Phone number updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the email and check uniqueness
    public void updateEmail(int passengerId, String newEmail) {
        HashSet<String> emailSet = getAllEmails();
        if (emailSet.contains(newEmail)) {
            System.out.println("Email already exists. Update failed.");
            return;
        }

        String sql = "UPDATE Passenger SET PassengerEmail = ? WHERE PassengerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, passengerId);
            preparedStatement.executeUpdate();

            System.out.println("Email updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the date of birth
    public void updateDob(int passengerId, Date newDob) {
        String sql = "UPDATE Passenger SET PassengerDOB = ? WHERE PassengerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, new java.sql.Date(newDob.getTime()));
            preparedStatement.setInt(2, passengerId);
            preparedStatement.executeUpdate();

            System.out.println("Date of Birth updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the DiscountPassId
    public void updateDiscountPassId(int passengerId, int newDiscountPassId) {
        DiscountPass dp = new DiscountPass(0);
        HashSet<Integer> hs = dp.getAllDiscountPassIDs();

        if (newDiscountPassId != 0 && !hs.contains(newDiscountPassId)) {
            System.out.println("Invalid Discount Pass ID. Update failed.");
            return;
        }

        String sql = "UPDATE Passenger SET DiscountPassID = ? WHERE PassengerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, newDiscountPassId);
            preparedStatement.setInt(2, passengerId);
            preparedStatement.executeUpdate();

            System.out.println("Discount Pass ID updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a list of passengers associated with a specific customer ID
    public ArrayList<Passenger> getPassengersByCustomerID(int customerID) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM Passenger WHERE AssociatedWith = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("PassengerID");
                String name = resultSet.getString("PassengerName");
                String phoneNumber = resultSet.getString("PassengerNumber");
                String email = resultSet.getString("PassengerEmail");
                Date dob = resultSet.getDate("PassengerDOB");
                int discountPassId = resultSet.getInt("DiscountPassID");
                int associatedWith = resultSet.getInt("AssociatedWith");

                Passenger passenger = new Passenger(id, name, phoneNumber, email, dob, discountPassId, associatedWith);
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }

    // Getters and setters

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getDiscountPassId() {
        return discountPassId;
    }

    public void setDiscountPassId(int discountPassId) {
        this.discountPassId = discountPassId;
    }

    public int getAssociatedWith() {
        return associatedWith;
    }

    public void setAssociatedWith(int associatedWith) {
        this.associatedWith = associatedWith;
    }

    public static void main(String[] args) {
        // Test instance creation with customerID
        Passenger passenger1 = new Passenger(1); // Assuming customerID is 1

        // Test instance retrieval from DB using passengerID
        Passenger passengerFromDB = passenger1.getPassengerFromDB(passenger1.getID());
        if (passengerFromDB != null) {
            System.out.println("Passenger Retrieved: " + passengerFromDB.getName());
        }

        // Test updating phone number
        passenger1.updatePhoneNumber(passenger1.getID(), "9876543210");

        // Test updating email
        passenger1.updateEmail(passenger1.getID(), "newemail@example.com");

        // Test updating name
        passenger1.updateName(passenger1.getID(), "New Name");

        // Test updating DOB
        passenger1.updateDob(passenger1.getID(), new Date());

        // Test updating discount pass ID
        passenger1.updateDiscountPassId(passenger1.getID(), 2);

        // Test retrieving passengers associated with a customerID
        ArrayList<Passenger> passengers = passenger1.getPassengersByCustomerID(1);
        System.out.println("Passengers associated with CustomerID 1:");
        for (Passenger p : passengers) {
            System.out.println("Passenger Name: " + p.getName() + ", Phone: " + p.getPhoneNumber());
        }
    }
}
