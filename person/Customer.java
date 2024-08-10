import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Customer {

    private int id;
    private String name;
    private String phoneNumber;
    private Date dob;
    private String email;
    private String password;
    private int discountPassId;

    // No-parameter constructor
    public Customer(String email) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Customer Details");

        System.out.println("Enter Name: ");
        this.name = scanner.nextLine();

        System.out.println("Enter Phone Number: ");
        this.phoneNumber = scanner.nextLine();
        while (!isValidPhoneNumber(this.phoneNumber)) {
            System.out.println("Invalid phone number. Please re-enter the phone number:");
            this.phoneNumber = scanner.nextLine();
        }
        this.email = email;
        System.out.println("Enter Password: ");
        this.password = scanner.nextLine();

        System.out.println("Enter Date of Birth (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine();
        this.dob = java.sql.Date.valueOf(dobInput); // Converting String to Date

        System.out.println("Enter Discount Pass ID: ");
        this.discountPassId = scanner.nextInt();

        // Call the method to add the customer to the database
        addCustomerToDB(this.name, this.phoneNumber, this.email, this.password, this.dob, this.discountPassId);
    }

    // Parameterized constructor
    public Customer(int id, String name, String phoneNumber, String email, String password, Date dob,
            int discountPassId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.discountPassId = discountPassId;
    }

    public Customer(int a) {
        // A constructor that is only to access methods
    }

    // Method to validate the phone number (example validation for 10-digit numbers)
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    // Method to add a customer to the database
    public void addCustomerToDB(String name, String phoneNumber, String email, String password, Date dob,
            int discountPassId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "root", "")) {
            String sql = "INSERT INTO Customer (CustomerName, CustomerNumber, CustomerEmail, Password, CustomerDOB, DiscountPassID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setDate(5, new java.sql.Date(dob.getTime()));
            preparedStatement.setInt(6, discountPassId);

            preparedStatement.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a customer from the database using the ID
    public Customer getCustomerFromDB(int customerId) {
        Customer customer = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "root", "")) {
            String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("CustomerID");
                String name = resultSet.getString("CustomerName");
                String phoneNumber = resultSet.getString("CustomerNumber");
                String email = resultSet.getString("CustomerEmail");
                String password = resultSet.getString("Password");
                Date dob = resultSet.getDate("CustomerDOB");
                int discountPassId = resultSet.getInt("DiscountPassID");

                customer = new Customer(id, name, phoneNumber, email, password, dob, discountPassId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDiscountPassId() {
        return discountPassId;
    }

    public void setDiscountPassId(int discountPassId) {
        this.discountPassId = discountPassId;
    }
    public boolean isValidEmail(String email) {
        // Simple validation to check the email format contains "@" and "."
        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');
        return atPosition > 0 && dotPosition > atPosition;
    }
    public static Customer getUserByEmail(String email) throws Exception {
        Customer user = null;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM customer WHERE CustomerEmail = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new Customer(10);
                user.setId(resultSet.getInt("CustomerID"));
                user.setName(resultSet.getString("CustomerName"));
                user.setEmail(resultSet.getString("CustomerEmail"));
             //   user.setExperience(resultSet.getString("experience"));
                user.setPhoneNumber(resultSet.getString("CustomerNumber"));
               user.dob=resultSet.getDate("CustomerDOB");
                user.setPassword(resultSet.getString("password"));
                user.setDiscountPassId(resultSet.getInt("DiscountPassID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
