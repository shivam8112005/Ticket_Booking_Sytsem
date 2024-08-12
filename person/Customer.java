import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

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
        this.addCustomerToDB(this.name, this.phoneNumber, this.email, this.password, this.dob, this.discountPassId);
       
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
            String query1="SELECT CustomerID FROM Customer WHERE CustomerEmail= ?";
            PreparedStatement pst=connection.prepareStatement(query1);
            pst.setString(1, this.getEmail());
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                int id=rs.getInt("CustomerID");
                this.setId(id);
            }
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
    
    public void bookTicket() throws Exception{
        Scanner scanner = new Scanner(System.in);

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        LocalDateTime startTime = null;
        LocalDateTime currentTime = LocalDateTime.now();
        
        while (true) {
            System.out.print("Enter Trip Start Time (YYYY-MM-DD HH:MM:SS): ");
            String input = scanner.nextLine();
            
            try {
                startTime = LocalDateTime.parse(input, formatter);
                
                if (startTime.isAfter(currentTime)) {
                    break; // Valid input, exit loop
                } else {
                    System.out.println("The start time must be greater than the current time. Please try again.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please enter the date and time in the format YYYY-MM-DD HH:MM:SS.");
            }
        }

        System.out.print("Enter Start Location: ");
        String startLocation = scanner.nextLine();

        System.out.print("Enter End Location: ");
        String endLocation = scanner.nextLine();

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT bus.BusID, bus.NumberPlate, bus.NumberOfSeats, trip.StartTime, trip.EndTime, trip.Price " +
                           "FROM bus " +
                           "JOIN trip ON bus.BusID = trip.BusID " +
                           "JOIN route ON trip.RouteID = route.RouteID " +
                           "WHERE trip.StartTime >= ? AND route.StartLocation = ? AND route.EndLocation = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                Timestamp startTimeTimestamp = Timestamp.valueOf(startTime);
                statement.setTimestamp(1, startTimeTimestamp);
                statement.setString(2, startLocation);
                statement.setString(3, endLocation);

                try (ResultSet resultSet = statement.executeQuery()) {
                    boolean busesFound = false;

                    while (resultSet.next()) {
                        busesFound = true;
                        int busId = resultSet.getInt("BusID");
                        String numberPlate = resultSet.getString("NumberPlate");
                        int numberOfSeats = resultSet.getInt("NumberOfSeats");
                        String tripStartTime = resultSet.getString("StartTime");
                        String tripEndTime = resultSet.getString("EndTime");
                        int tripId=resultSet.getInt("TripID");
                        double price = resultSet.getDouble("Price");

                        System.out.println("Bus ID: " + busId);
                        System.out.println("Trip ID: "+tripId);
                        System.out.println("Number Plate: " + numberPlate);
                        System.out.println("Number of Available Seats: " + numberOfSeats);
                        System.out.println("Trip Start Time: " + tripStartTime);
                        System.out.println("Trip End Time: " + tripEndTime);
                        System.out.println("Price: $" + price);
                        System.out.println("-----------------------------------------");
                    }

                    if (!busesFound) {
                        System.out.println("No buses available for the selected trip.");
                        return;
                    }
                    System.out.print("enter Bus Id to book Ticket: ");
                    int n=scanner.nextInt();
                    String querry="SELECT NumberOfSeats FROM bus WHERE busId=?";
                    PreparedStatement pst=connection.prepareStatement(querry);
                    pst.setInt(1, n);
                    ResultSet rs=pst.executeQuery();
                    if(rs.next()){
                        System.out.println("Number of Available Seats: " + rs.getInt("NumberOfSeats"));
                        if(rs.getInt("NumberOfSeats")>0){
                            System.out.print("Enter number of seats: ");
                            int seats=scanner.nextInt();
                            if(seats>rs.getInt("NumberOfSeats")){
                                System.out.println(seats+ "seats Not available!");
                                return;
                            }else{
                                String sql="INSERT INTO ticket(tripid, bookedby, bookedfor, booktime) VALUES(?, ?, ?, ?)";
                                String sql1="SELECT trip.TripID FROM trip INNER JOIN bus ON trip.BusID = bus.BusID WHERE bus.BusID = ?";
                                PreparedStatement pst2=connection.prepareStatement(sql1);
                                pst2.setInt(1, n);
                                ResultSet rs1=pst2.executeQuery();
                                PreparedStatement pst1=connection.prepareStatement(sql);
                                for(int i=1;i<=seats;i++){
                                    //give options of passengers for which user have already booked ticket for in past
                                    System.out.println("Enter Passenger "+i+" Details: ");
                                    Passenger p=new Passenger();
                                    pst1.setInt(1, rs1.getInt("TripID"));
                                    pst1.setInt(2, this.getID());
                                    pst1.setInt(3, p.getID());
                                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                    pst1.setTimestamp(4, currentTimestamp);
                                    int r=pst1.executeUpdate();
                                }
                                String querry1="UPDATE bus set NumberOfSeats=? where busid=?";
                                PreparedStatement ps=connection.prepareStatement(querry1);
                                ps.setInt(1, rs.getInt("NumberOfSeats")-seats);
                                ps.setInt(2, n);
                                ps.executeUpdate();
                            }
                        }
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
