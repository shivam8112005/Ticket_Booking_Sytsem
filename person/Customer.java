package person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.Statement;


import model.DiscountPass;
import model.Ticket;

public class Customer {

    private int id;
    private String name;
    private String phoneNumber;
    private Date dob;
    private String email;
    private String userPassword;
    private int discountPassId;

    private final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String USER = "root";
    private final String PASSWORD = "";

    // No-parameter constructor for register
    public Customer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---Register---");

        System.out.print("Enter Name: ");
        this.name = scanner.nextLine();

        this.phoneNumber = getValidPhoneNumber();

        this.email = getValidEmail();

        System.out.print("Enter Password: ");
        this.userPassword = getValidPassword();

        this.dob = getValidDOB();

        DiscountPass dp = new DiscountPass(0);
        HashSet<Integer> validDiscountPassIDs = dp.getAllDiscountPassIDs();

        while (true) {
            dp.printAllDiscountPasses();
            System.out.print("Enter Discount Pass ID: ");
            try {
                this.discountPassId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (validDiscountPassIDs.contains(this.discountPassId)) {
                    System.out.println("Discount Pass set!");
                    break;
                } else {
                    System.out.println("Invalid Discount Pass ID. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric Discount Pass ID.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Call the method to add the customer to the database
        this.addCustomerToDB(this.name, this.phoneNumber, this.email, this.userPassword, this.dob, this.discountPassId);
    }

    // Parameterized constructor for login
    public Customer(String phoneNumber, String userPassword) {
        loginCustomerFromDB(phoneNumber, userPassword);
    }

    public Customer(int a) {
        // A constructor that is only to access methods
    }

    // Method to validate the phone number
    String getValidPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> existingNumbers = getAllPhoneNumbers();
        String phoneNumber;

        do {
            System.out.print("Enter Phone Number: ");
            phoneNumber = scanner.nextLine();

            if (!phoneNumber.matches("\\d{10}")) {
                System.out.println("Invalid number! Please enter a 10-digit number.");
            } else if (existingNumbers.contains(phoneNumber)) {
                System.out.println("This number already exists! Please enter a different number.");
            } else {
                break;
            }
        } while (true);

        return phoneNumber;
    }

    String getValidEmail() {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> existingEmails = getAllEmails();
        String email;

        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("Invalid email format. Please re-enter your email:");
            } else if (existingEmails.contains(email)) {
                System.out.println("This email already exists! Please enter a different email:");
            } else {
                break;
            }
        }

        return email;
    }

    public String getValidPassword() {
        Scanner scanner = new Scanner(System.in);
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

    // Method to add a customer to the database
    public void addCustomerToDB(String name, String phoneNumber, String email, String password, Date dob,
            int discountPassId) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
            String query1 = "SELECT CustomerID FROM Customer WHERE CustomerEmail= ?";
            PreparedStatement pst = connection.prepareStatement(query1);
            pst.setString(1, this.getEmail());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("CustomerID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private java.sql.Date getValidDOB() {
        Scanner scanner = new Scanner(System.in);
        java.sql.Date date = null;
    
        while (true) {
            System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
            String dobInput = scanner.nextLine();
    
            try {
                // Convert the input string to a LocalDate
                LocalDate dob = LocalDate.parse(dobInput);
    
                // Get the current date
                LocalDate currentDate = LocalDate.now();
    
                // Check if the entered DOB is not in the future
                if (dob.isAfter(currentDate)) {
                    System.out.println("Error: Date of Birth cannot be in the future.");
                    continue; // Ask the user to enter the date again
                }
    
                // Convert LocalDate to java.sql.Date
                date = java.sql.Date.valueOf(dob);
                break; // If successful and valid, break the loop
    
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
            }
        }
    
        return date;
    }

    // Method to retrieve a customer from the database using the ID
    public Customer loginCustomerFromDB(String email, String pass) {
        Customer customer = null;

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM Customer WHERE CustomerEmail = ? AND Password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.id = resultSet.getInt("CustomerID");
                this.name = resultSet.getString("CustomerName");
                this.phoneNumber = resultSet.getString("CustomerNumber");
                this.email = resultSet.getString("CustomerEmail");
                this.userPassword = resultSet.getString("Password");
                this.dob = resultSet.getDate("CustomerDOB");
                this.discountPassId = resultSet.getInt("DiscountPassID");

            } else {
                System.out.println("No such account");
                System.exit(0);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return userPassword;
    }

    public int getDiscountPassId() {
        return discountPassId;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Implement phone number validation logic
        return true; // Placeholder
    }

    public void bookTicketDisplay() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Ask for start location and end location
        System.out.print("Enter Start Location: ");
        String startLocation = scanner.nextLine();

        System.out.print("Enter End Location: ");
        String endLocation = scanner.nextLine();

        // Step 2: Find RouteID using the start and end location
        int routeId = -1;
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String routeQuery = "SELECT RouteID FROM Route WHERE StartLocation = ? AND EndLocation = ?";
            PreparedStatement routeStatement = connection.prepareStatement(routeQuery);
            routeStatement.setString(1, startLocation);
            routeStatement.setString(2, endLocation);
            ResultSet routeResultSet = routeStatement.executeQuery();
            if (routeResultSet.next()) {
                routeId = routeResultSet.getInt("RouteID");
            } else {
                System.out.println("No route found for the given locations.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Step 3: Ask for the departure date
        System.out.print("Enter Departure Date (yyyy-mm-dd): ");
        String departureDateInput = scanner.nextLine();
        LocalDateTime departureDate = null;
        try {
            departureDate = LocalDateTime.parse(departureDateInput + "T00:00:00",
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
            return;
        }

        // Step 4: Find all trips planned on that day with the above RouteID
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String tripQuery = "SELECT t.TripID, r.StartLocation, r.EndLocation, t.Price, t.StartTime, t.EndTime " +
                    "FROM Trip t " +
                    "JOIN Route r ON t.RouteID = r.RouteID " +
                    "WHERE t.RouteID = ? AND DATE(t.StartTime) = ?";
            PreparedStatement tripStatement = connection.prepareStatement(tripQuery);
            tripStatement.setInt(1, routeId);
            tripStatement.setTimestamp(2, Timestamp.valueOf(departureDate));
            ResultSet tripResultSet = tripStatement.executeQuery();

            boolean tripsFound = false;
            while (tripResultSet.next()) {
                tripsFound = true;
                int tripId = tripResultSet.getInt("TripID");
                String startLoc = tripResultSet.getString("StartLocation");
                String endLoc = tripResultSet.getString("EndLocation");
                double price = tripResultSet.getDouble("Price");
                Timestamp startTime = tripResultSet.getTimestamp("StartTime");
                Timestamp endTime = tripResultSet.getTimestamp("EndTime");

                System.out.println();
                System.out.println("Trip ID: " + tripId);
                System.out.println("From: " + startLoc + " To: " + endLoc);
                System.out.println("Price: " + price);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                System.out.println("-----------------------------");

                // Call the ticketProcessing method for each trip found
                ticketProcessing(tripId);
            }

            if (!tripsFound) {
                System.out.println("No trips found for the selected date and route.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ticketProcessing(int tripId) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Build table name dynamically
        String tableName = "tripseat_" + tripId;

        // Step 2: Ask for number of seats to book
        System.out.print("Enter the number of seats to book: ");
        int seatsToBook = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Check availability
        boolean seatsAvailable = checkSeatAvailability(tableName, seatsToBook);

        if (seatsAvailable) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

                // Retrieve and display associated passengers
                Passenger ps = new Passenger('c');
                ArrayList<Passenger> passengers = ps.getPassengersByCustomerID(this.id);

                if (passengers.isEmpty()) {
                    System.out.println("No associated passengers found.");
                    return;
                }

                // Create a HashSet to store valid passenger IDs
                HashSet<Integer> validPassengerIDs = new HashSet<>();
                System.out.println("Select a passenger for each ticket:");
                for (Passenger passenger : passengers) {
                    System.out.println("Passenger ID: " + passenger.getID() + ", Name: " + passenger.getName());
                    validPassengerIDs.add(passenger.getID()); // Add IDs to the HashSet
                }

                // Book the seats
                for (int i = 0; i < seatsToBook; i++) {
                    int selectedPassengerID;
                    while (true) {
                        System.out.print("Enter Passenger ID for ticket " + (i + 1) + ": ");
                        selectedPassengerID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline

                        if (validPassengerIDs.contains(selectedPassengerID)) {
                            break; // Exit loop if ID is valid
                        } else {
                            System.out.println("Invalid Passenger ID. Please enter a valid Passenger ID.");
                        }
                    }

                    // Insert the ticket and get the ticket ID
                    int ticketId = getLastInsertedTicketId(connection, tripId, selectedPassengerID);
                    updateSeatAvailability(tableName, ticketId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Not enough seats available.");
        }
    }

    private boolean checkSeatAvailability(String tableName, int seatsRequired) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE TicketID IS NULL";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int availableSeats = resultSet.getInt(1);
                return availableSeats >= seatsRequired;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateSeatAvailability(String tableName, int ticketId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Step 1: Find the row with NULL TicketID
            String selectSql = "SELECT SeatNumber FROM " + tableName + " WHERE TicketID IS NULL LIMIT 1";

            int rowId = -1; // Variable to hold the row ID

            try (Statement selectStatement = connection.createStatement();
                    ResultSet resultSet = selectStatement.executeQuery(selectSql)) {
                if (resultSet.next()) {
                    rowId = resultSet.getInt("SeatNumber"); 
                }
            }

            if (rowId != -1) {
                // Step 2: Update the selected row
                String updateSql = "UPDATE " + tableName + " SET TicketID = ? WHERE SeatNumber = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, ticketId);
                    updateStatement.setInt(2, rowId);
                    updateStatement.executeUpdate();
                }
            } else {
                System.out.println("No available seats to update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLastInsertedTicketId(Connection connection, int tripId, int selectedPassengerID) {
        try {
            String sql = "INSERT INTO ticket (TripID, BookedBy, BookedFor, BookTime) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, tripId);
                pstmt.setInt(2, this.id);
                pstmt.setInt(3, selectedPassengerID);

                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve last inserted ticket ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void printBookedTicketHistory() {
        int bookedById = this.id;
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // SQL query to get ticket history with joins
            String sql = "SELECT t.TicketID, t.TripID, r.StartLocation, r.EndLocation, " +
                    "tr.StartTime, tr.EndTime, " +
                    "c.CustomerName AS BookedByName, " +
                    "p.PassengerName AS BookedForName " +
                    "FROM Ticket t " +
                    "JOIN Trip tr ON t.TripID = tr.TripID " +
                    "JOIN Route r ON tr.RouteID = r.RouteID " +
                    "JOIN Customer c ON t.BookedBy = c.CustomerID " +
                    "JOIN Passenger p ON t.BookedFor = p.PassengerID " +
                    "WHERE t.BookedBy = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookedById);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("TicketID");
                int tripId = resultSet.getInt("TripID");
                String startLocation = resultSet.getString("StartLocation");
                String endLocation = resultSet.getString("EndLocation");
                Timestamp startTime = resultSet.getTimestamp("StartTime");
                Timestamp endTime = resultSet.getTimestamp("EndTime");
                String bookedByName = resultSet.getString("BookedByName");
                String bookedForName = resultSet.getString("BookedForName");

                System.out.println();
                System.out.println("Ticket ID: " + ticketId);
                System.out.println("Trip ID: " + tripId);
                System.out.println("From: " + startLocation + " To: " + endLocation);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                System.out.println("Booked By: " + bookedByName);
                System.out.println("Booked For: " + bookedForName);
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upcomingJourneys() throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        String sql = "SELECT * from ticket where bookedby=?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, this.getID());
        ResultSet rs = pst.executeQuery();
        String sql1 = "SELECT * from trip where TripID=? AND StartTime > NOW()";
        PreparedStatement pst1 = connection.prepareStatement(sql1);
        int i = 1;
        while (rs.next()) {
            pst1.setInt(1, rs.getInt("TripID"));
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                String sql2 = "SELECT * FROM passenger WHERE passengerID=?";
                PreparedStatement pst2 = connection.prepareStatement(sql2);
                pst2.setInt(1, rs.getInt("bookedfor"));
                ResultSet rs2 = pst2.executeQuery();
                if (rs2.next()) {
                    System.out.println(i + ".  Passenger Name: " + rs2.getString("PassengerName") + "  Passenger ID:"
                            + rs2.getInt("passengerID") + "  Ticket ID:" + rs.getInt("TicketID"));
                    i++;
                }

            }
        }
        System.out.println("0. Return");
        if (i == 1) {
            System.out.println("No Up Cominng Journeys.");
            return;
        }
        System.out.print("Enter Ticket ID to view Ticket Details:");
        int ch = sc.nextInt();
        while (ch < 1 || ch >= i) {// this part is causing problems
            System.out.println("Enter Valid Ticket ID!");
            ch = sc.nextInt();
        }
        if (ch == 0) {
            return;
        }
        String q1 = "select * from ticket where TicketID=?";
        PreparedStatement pst2 = connection.prepareStatement(q1);
        pst2.setInt(1, ch);
        System.out.println("---------------------------- Ticket Details --------------------------");
        ResultSet rs2 = pst2.executeQuery();
        String sql2 = "SELECT * FROM trip where tripID=?";
        PreparedStatement pst3 = connection.prepareStatement(sql2);
        if (rs2.next()) {
            System.out.println("Ticket ID: " + rs2.getInt("TicketID"));
            System.out.println("Ticket Booking Time: " + rs2.getTimestamp("BookTime"));
            pst3.setInt(1, rs2.getInt("TripID"));
            ResultSet rs3 = pst3.executeQuery();

            if (rs3.next()) {
                System.out.println("Ticket Price: " + rs3.getDouble("Price"));
                System.out.println("------------------------------- trip Details --------------------------");
                // System.out.println("Trip ID: "+rs3.getInt("TripID"));
                System.out.println("Trip Start Time: " + rs3.getTimestamp("StartTime"));
                System.out.println("Trip End Time: " + rs3.getTimestamp("EndTime"));
                String sql3 = "select * from route where RouteID=?";
                PreparedStatement pst4 = connection.prepareStatement(sql3);
                pst4.setInt(1, rs3.getInt("RouteID"));
                ResultSet rs4 = pst4.executeQuery();
                if (rs4.next()) {
                    System.out.println("Start Location: " + rs4.getString("StartLocation"));
                    System.out.println("End Location: " + rs4.getString("EndLocation"));
                }
            }
        }
        System.out.println("------------------------------------ Passenger Details ---------------------------");
        pst1.setInt(1, ch);
        ResultSet r = pst1.executeQuery();
        if (r.next()) {
            System.out.println("Passenger Name: " + r.getString("PassengerName"));
            System.out.println("Passenger Mobile number: " + r.getString("PassengerNumber"));
            System.out.println("Passenger Email: " + r.getString("PassengerEmail"));
            System.out.println("Passenger DOB: " + r.getDate("PassengerDOB"));
            System.out.println("Passenger Discount pass ID: " + r.getInt("DiscountPassID "));
        }
    }

    public HashSet<String> getAllPhoneNumbers() {
        HashSet<String> phoneNumberSet = new HashSet<>();
        String sql = "SELECT CustomerNumber FROM Customer";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                phoneNumberSet.add(resultSet.getString("CustomerNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phoneNumberSet;
    }

    // Method to get all emails from the database
    public HashSet<String> getAllEmails() {
        HashSet<String> emailSet = new HashSet<>();
        String sql = "SELECT CustomerEmail FROM Customer";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                emailSet.add(resultSet.getString("CustomerEmail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailSet;
    }

    // Method to update the name
    public void updateName(int customerId, String newName) {
        String sql = "UPDATE Customer SET CustomerName = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Name updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the phone number and check uniqueness
    public void updatePhoneNumber(int customerId, String newPhoneNumber) {
        HashSet<String> phoneNumberSet = getAllPhoneNumbers();
        if (!isValidPhoneNumber(newPhoneNumber) || phoneNumberSet.contains(newPhoneNumber)) {
            System.out.println("Invalid or duplicate phone number. Update failed.");
            return;
        }

        String sql = "UPDATE Customer SET CustomerNumber = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPhoneNumber);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Phone number updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the email and check uniqueness
    public void updateEmail(int customerId, String newEmail) {
        HashSet<String> emailSet = getAllEmails();
        if (emailSet.contains(newEmail)) {
            System.out.println("Email already exists. Update failed.");
            return;
        }

        String sql = "UPDATE Customer SET CustomerEmail = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Email updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the date of birth
    public void updateDob(int customerId, Date newDob) {
        String sql = "UPDATE Customer SET CustomerDOB = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, new java.sql.Date(newDob.getTime()));
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Date of Birth updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the DiscountPassId
    public void updateDiscountPassId(int customerId, int newDiscountPassId) {
        DiscountPass dp = new DiscountPass(0);
        HashSet<Integer> hs = dp.getAllDiscountPassIDs();

        if (newDiscountPassId != 0 && !hs.contains(newDiscountPassId)) {
            System.out.println("Invalid Discount Pass ID. Update failed.");
            return;
        }

        String sql = "UPDATE Customer SET DiscountPassID = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, newDiscountPassId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Discount Pass ID updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the password
    public void updatePassword(int customerId, String newPassword) {
        String sql = "UPDATE Customer SET Password = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();

            System.out.println("Password updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void profileMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nProfile Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Update Details");
            System.out.println("3. Add Passenger");
            System.out.println("4. Update Passenger");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    updateDetails();
                    break;
                case 3:
                    addPassenger();
                    break;
                case 4:
                    updatePassenger();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewProfile() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT c.CustomerName, c.CustomerNumber, c.CustomerEmail, c.CustomerDOB, c.DiscountPassID, dp.PassName "
                    +
                    "FROM Customer c " +
                    "JOIN DiscountPass dp ON c.DiscountPassID = dp.DiscountPassID " +
                    "WHERE c.CustomerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("CustomerName"));
                System.out.println("Phone Number: " + resultSet.getString("CustomerNumber"));
                System.out.println("Email: " + resultSet.getString("CustomerEmail"));
                System.out.println("DOB: " + resultSet.getDate("CustomerDOB"));
                System.out.println("Discount Pass ID: " + resultSet.getInt("DiscountPassID"));
                System.out.println("Discount Pass Name: " + resultSet.getString("PassName"));
            } else {
                System.out.println("Profile not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDetails() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nUpdate Menu:");
            System.out.println("1. Update Name");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Email");
            System.out.println("4. Update DOB");
            System.out.println("5. Update Discount Pass ID");
            System.out.println("6. Update Password");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    updateName(this.id, newName);
                    break;
                case 2:
                    System.out.print("Enter new Phone Number: ");
                    String newPhoneNumber = getValidPhoneNumber();
                    updatePhoneNumber(this.id, newPhoneNumber);
                    break;
                case 3:
                    System.out.print("Enter new Email: ");
                    String newEmail = getValidEmail();
                    updateEmail(this.id, newEmail);
                    break;
                case 4:
                    // Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new DOB (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine();

        try {
            // Parse the input date
            LocalDate newDob = LocalDate.parse(dobInput);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Check if the entered DOB is not in the future
            if (newDob.isAfter(currentDate)) {
                System.out.println("Error: Date of Birth cannot be in the future.");
                return;
            }

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(newDob);

            // Proceed to update the DOB
            updateDob(this.id, sqlDate);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
        }
                   // updateDob(this.id, newDob);
                    break;
                case 5:
                    DiscountPass dp = new DiscountPass(0);
                    dp.printAllDiscountPasses();
                    System.out.print("Enter new Discount Pass ID: ");
                    int newDiscountPassId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    updateDiscountPassId(this.id, newDiscountPassId);
                    break;
                case 6:
                    System.out.print("Enter new Password: ");
                    String newPassword = getValidPassword();
                    updatePassword(this.id, newPassword);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addPassenger() {
        // Assuming that adding a passenger involves creating a new Passenger instance
        new Passenger(this.id);
    }

    private void updatePassenger() {
        Scanner scanner = new Scanner(System.in);
        Passenger callMethod = new Passenger('a');

        ArrayList<Passenger> passengers = callMethod.getPassengersByCustomerID(this.id);

        if (passengers.isEmpty()) {
            System.out.println("No passengers found.");
            return;
        }

        System.out.println("Passengers:");
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println((i + 1) + ". " + passengers.get(i).getName());
        }

        System.out.print("Enter the number of the passenger you want to update: ");
        int passengerChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (passengerChoice < 1 || passengerChoice > passengers.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Passenger selectedPassenger = passengers.get(passengerChoice - 1);

        // Call the update menu for the selected passenger
        updatePassengerDetails(selectedPassenger);
    }

    private void updatePassengerDetails(Passenger passenger) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nUpdate Passenger Details:");
            System.out.println("1. Update Name");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Email");
            System.out.println("4. Update DOB");
            System.out.println("5. Update Discount Pass ID");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    passenger.updateName(passenger.getID(), newName);
                    break;
                case 2:
                    System.out.print("Enter new Phone Number: ");
                    String newPhoneNumber = scanner.nextLine();
                    passenger.updatePhoneNumber(passenger.getID(), newPhoneNumber);
                    break;
                case 3:
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    passenger.updateEmail(passenger.getID(), newEmail);
                    break;
                case 4:
                boolean validDob = false;
                LocalDate newDob = null;
        
                while (!validDob) {
                    System.out.print("Enter new DOB (yyyy-mm-dd): ");
                    String dobInput = scanner.nextLine();
                    
                    try {
                        newDob = LocalDate.parse(dobInput);
                        // Check if the date is in the future
                        if (newDob.isAfter(LocalDate.now())) {
                            System.out.println("DOB cannot be a future date. Please enter a valid date.");
                        } else {
                            validDob = true;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in the format yyyy-mm-dd.");
                    }
                }
        
                // Convert LocalDate to java.sql.Date for database update
                java.sql.Date sqlDate = java.sql.Date.valueOf(newDob);
                passenger.updateDob(passenger.getID(), sqlDate);
                System.out.println("DOB updated successfully.");
                    break;
                case 5:
                    System.out.print("Enter new Discount Pass ID: ");
                    int newDiscountPassId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    passenger.updateDiscountPassId(passenger.getID(), newDiscountPassId);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return; // Exit the update menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
