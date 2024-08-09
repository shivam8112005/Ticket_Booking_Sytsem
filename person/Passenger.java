import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Passenger {

    private int id;
    private String name;
    private String phoneNumber;
    private Date dob;
    private String email;
    private int discountPassId;

    // No-parameter constructor
    public Passenger() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Passenger Details");

        System.out.println("Enter Name: ");
        this.name = scanner.nextLine();

        System.out.println("Enter Phone Number: ");
        this.phoneNumber = scanner.nextLine();
        while (!isValidPhoneNumber(this.phoneNumber)) {
            System.out.println("Invalid phone number. Please re-enter the phone number:");
            this.phoneNumber = scanner.nextLine();
        }

        System.out.println("Enter Email: ");
        this.email = scanner.nextLine();

        System.out.println("Enter Date of Birth (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine();
        this.dob = java.sql.Date.valueOf(dobInput); // Converting String to Date

        System.out.println("Enter Discount Pass ID: ");
        this.discountPassId = scanner.nextInt();

        // Call the method to add the passenger to the database
        addPassengerToDB(this.name, this.phoneNumber, this.email, this.dob, this.discountPassId);
    }

    // Parameterized constructor
    public Passenger(int id, String name, String phoneNumber, String email, Date dob, int discountPassId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dob = dob;
        this.discountPassId = discountPassId;
    }

    public Passenger(int a) {
        // A constructor that is only to access methods
    }

    // Method to validate the phone number (example validation for 10-digit numbers)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    // Method to add a passenger to the database
    public void addPassengerToDB(String name, String phoneNumber, String email, Date dob, int discountPassId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "username", "password")) {
            String sql = "INSERT INTO Passenger (PassengerName, PassengerNumber, PassengerEmail, PassengerDOB, DiscountPassID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, new java.sql.Date(dob.getTime()));
            preparedStatement.setInt(5, discountPassId);

            preparedStatement.executeUpdate();
            System.out.println("Passenger added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a passenger from the database using the ID
    public Passenger getPassengerFromDB(int passengerId) {
        Passenger passenger = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "username", "password")) {
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

                passenger = new Passenger(id, name, phoneNumber, email, dob, discountPassId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passenger;
    }

    // Getters and setters
    public int getID() {
        return id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiscountPassId() {
        return discountPassId;
    }

    public void setDiscountPassId(int discountPassId) {
        this.discountPassId = discountPassId;
    }

}
