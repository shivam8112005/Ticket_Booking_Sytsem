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

import com.mysql.cj.xdevapi.PreparableStatement;

import DataStructure.InputValidator;

import java.util.LinkedList;

//import com.mysql.cj.exceptions.ExceptionFactory;
import java.sql.Clob;

import java.sql.Statement;

import java.io.*;
import model.DiscountPass;



public class Customer implements util{
    private int id;
    private String name;
    private String phoneNumber;
    private Date dob;
    private String email;
    private String userPassword;
    private int discountPassId;
   // HashSet<String> emailSet;
    private final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String USER = "root";
    private final String PASSWORD = "";
    public static Scanner scanner=new Scanner(System.in);
static InputValidator ip=new InputValidator();
    // No-parameter constructor for register
    public Customer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("------------------------- Register -----------------------");

        System.out.print("Enter Name: ");
        this.name = scanner.nextLine();

        this.phoneNumber = getValidPhoneNumber();

        this.email = getValidEmail();

       // System.out.print("Enter Password: ");
        this.userPassword = setValidPassword();

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
        this.id=this.saveToDB();
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
@Override
    public String setValidPassword() {
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
    @Override
    public int saveToDB() {
        try {
            String name=this.getName();
            String phoneNumber=this.getPhoneNumber();
            String email=this.getEmail();
            String password=ip.encryptPassword(this.getPassword());
            Date dob=this.getDob();
            int discountPassId=this.getDiscountPassId();
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
               return rs.getInt("CustomerID");
            }
        } catch (SQLException e) {
            System.out.println((e.getMessage())+" Try Again !");
           // throw new RuntimeException(e);
        }return -1;
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
String passw=ip.encryptPassword(pass);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM Customer WHERE CustomerEmail = ? AND Password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passw);
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
        System.out.println("-------------------------------- Book Ticket -------------------------------");
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
                   "WHERE t.RouteID = ? AND DATE(t.StartTime) = ? AND t.StartTime >= NOW() + INTERVAL 10 MINUTE";
            PreparedStatement tripStatement = connection.prepareStatement(tripQuery);
            tripStatement.setInt(1, routeId);
            tripStatement.setTimestamp(2, Timestamp.valueOf(departureDate));
            ResultSet tripResultSet = tripStatement.executeQuery();
            // String q1="select tripid from ticket where bookedby=? ";
            // PreparedStatement ps=connection.prepareStatement(q1);
            // ps.setInt(1, this.getID());
            // ResultSet r=ps.executeQuery();
          //  HashSet<Integer> hs=new HashSet<>();
            // while(r.next()){
            //     hs.add(r.getInt("tripid"));
            // }
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
            //    if(hs.size()>=3){
            //     System.out.println("Price: "+(price/2));
            // }else{
            //     System.out.println("Price: "+price);
            // }
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                System.out.println("-----------------------------");

                // Call the ticketProcessing method for each trip found
                
            }
            System.out.print("Enter Trip Id to Book Ticket: ");
            int n=scanner.nextInt();
            String tripQuery1 = "SELECT t.TripID, r.StartLocation, r.EndLocation, t.Price, t.StartTime, t.EndTime " +
                   "FROM Trip t " +
                   "JOIN Route r ON t.RouteID = r.RouteID " +
                   "WHERE t.TripId= ? AND t.RouteID = ? AND DATE(t.StartTime) = ? AND t.StartTime >= NOW() + INTERVAL 10 MINUTE";
            PreparedStatement tripStatement1 = connection.prepareStatement(tripQuery1);
            tripStatement1.setInt(1, n);
            tripStatement1.setInt(2, routeId);
            tripStatement1.setTimestamp(3, Timestamp.valueOf(departureDate));
            ResultSet tripResultSet1 = tripStatement1.executeQuery();
            while(!tripResultSet1.next()){
                System.out.print("Invalid Trip Id ! enter Trip ID or 0 to return: ");
                n=scanner.nextInt();
                if(n==0){
                    return;
                }
                tripStatement1.setInt(1, n);
            tripStatement1.setInt(2, routeId);
            tripStatement1.setTimestamp(3, Timestamp.valueOf(departureDate));
            tripResultSet1 = tripStatement1.executeQuery();
            }
            ticketProcessing(n);

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
        //LinkedList<Integer> tid=new LinkedList<>();
        DataStructure.LinkedList<Integer> tid = new DataStructure.LinkedList<>();

        // Step 2: Ask for number of seats to book
        System.out.print("Enter the number of seats to book: ");
        int seatsToBook = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Check availability
        boolean seatsAvailable = checkSeatAvailability(tableName, seatsToBook);

        if (seatsAvailable) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

                // Retrieve and display associated passengers
                //System.out.println("fgernfiernk 11111111111111");
                Passenger ps = new Passenger('c');
              //  System.out.println("lgfiernernjn 222222222222222");
              DataStructure.ArrayList<Passenger> passengers = ps.getPassengersByCustomerID(this.id);
              //  System.out.println(passengers);
           //   System.out.println(passengers);
        //    String s="n";
        //         if (passengers.isEmpty()) {
        //             System.out.println("No associated passengers found.");
        //             System.out.print("Add passenger + (y/n)?: ");
        //              s=scanner.next();
        //             if(s.equalsIgnoreCase("y")){
        //                 Passenger p=new Passenger(this.id);
        //                 int ticketId = getLastInsertedTicketId(connection, tripId, p.getID());
        //             tid.add(ticketId);
        //             updateSeatAvailability(tableName, ticketId);
        //             }else{
        //                 return;
        //             }
        //         }

                // Create a HashSet to store valid passenger IDs
                HashSet<Integer> validPassengerIDs = new HashSet<>();
                System.out.println("Select a passenger for each ticket:");
                // for (int i=0;i<passengers.size();i++) {
                //     System.out.println("Passenger ID: " + passengers.get(i).getID() + ", Name: " + passengers.get(i).getName());
                //     validPassengerIDs.add(passengers.get(i).getID()); // Add IDs to the HashSet
                // }
                
                // Book the seats
                // int j=0;
                // if(s.equalsIgnoreCase("y")){
                //     j=1;
                // }
                for (int i = 0; i < seatsToBook; i++) {
                    System.out.println("------------------------------------------");
                    for (int j=0;j<passengers.size();j++) {
                        System.out.println("Passenger ID: " + passengers.get(j).getID() + ", Name: " + passengers.get(j).getName());
                        validPassengerIDs.add(passengers.get(j).getID()); // Add IDs to the HashSet
                    }System.out.println("0. Add Passenger + :");
                    System.out.println("------------------------------------------");
                    int selectedPassengerID;
                    while (true) {
                        System.out.print("Enter Passenger ID for ticket " + (i + 1) + ": ");
                        selectedPassengerID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline

                        if (validPassengerIDs.contains(selectedPassengerID)) {
                            break; // Exit loop if ID is valid
                        }else if(selectedPassengerID==0){
                            Passenger p=new Passenger(this.id);
                            selectedPassengerID=p.getID();
                            break;
                        } else {
                            System.out.println("Invalid Passenger ID. Please enter a valid Passenger ID.");
                        }
                    }

                    // Insert the ticket and get the ticket ID
                    int ticketId = getLastInsertedTicketId(connection, tripId, selectedPassengerID);
                    tid.add(ticketId);
                    updateSeatAvailability(tableName, ticketId);
                }
                writeTicket(tid, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Ticket Booked Successfully.");
        } else {
            System.out.println("Not enough seats available.");
        }
    }
    public void writeTicket(DataStructure.LinkedList<Integer> l, Connection connection){
        File f=new File("D:/shivam/Tickets");
        try {
            FileWriter fw=new FileWriter(f,true);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write("---------------------------- Ticket Details --------------------------");
            bw.newLine();
            for(int i=0;i<l.size();i++){
                String q1 = "select * from ticket where TicketID=?";
                PreparedStatement pst2 = connection.prepareStatement(q1);
                pst2.setInt(1, l.get(i));
               // System.out.println("---------------------------- Ticket Details --------------------------");
                ResultSet rs2 = pst2.executeQuery();
                String sql2 = "SELECT * FROM trip where tripID=?";
                PreparedStatement pst3 = connection.prepareStatement(sql2);
                if (rs2.next()) {
                   if(i==0){
                    bw.write("Ticket ID: " + rs2.getInt("TicketID"));
                    bw.newLine();
                    bw.write("Ticket Booking Time: " + rs2.getTimestamp("BookTime"));
                    bw.newLine();
                    pst3.setInt(1, rs2.getInt("TripID"));
                    ResultSet rs3 = pst3.executeQuery();
        
                    if (rs3.next()) {
                        bw.write("Ticket Price: " + rs3.getDouble("Price"));
                        bw.newLine();
                        bw.write("------------------------------- trip Details --------------------------");
                        bw.newLine();
                        // System.out.println("Trip ID: "+rs3.getInt("TripID"));
                        bw.write("Trip Start Time: " + rs3.getTimestamp("StartTime"));
                        bw.newLine();
                        bw.write("Trip End Time: " + rs3.getTimestamp("EndTime"));
                        bw.newLine();
                        String sql3 = "select * from route where RouteID=?";
                        PreparedStatement pst4 = connection.prepareStatement(sql3);
                        pst4.setInt(1, rs3.getInt("RouteID"));
                        ResultSet rs4 = pst4.executeQuery();
                        if (rs4.next()) {
                            bw.write("Start Location: " + rs4.getString("StartLocation"));
                            bw.newLine();
                            bw.write("End Location: " + rs4.getString("EndLocation"));
                            bw.newLine();
                        }
                    }
                    bw.write("---------------------------------- Passenger Details ---------------------------");
                    bw.newLine();
                   }
                    
                    String q="select * from passenger where passengerid=?";
                    PreparedStatement ps=connection.prepareStatement(q);
                    String g="select passname from discountpass where discountpassid=?";
                    PreparedStatement pp=connection.prepareStatement(g);
                    
                    ps.setInt(1,rs2.getInt("bookedfor"));
                    ResultSet r=ps.executeQuery();
                    if(r.next()){
                        bw.newLine();
                        pp.setInt(1,r.getInt("DiscountPassID"));
                        ResultSet r1=pp.executeQuery();
                        bw.write((i+1)+". Passenger Name: "+r.getString("passengername"));
                        bw.newLine();
                        bw.write("passenger PhoneNumber: "+r.getString("PassengerNumber"));
                        bw.newLine();
                        bw.write("Passenger Email: "+r.getString("PassengerEmail"));
                        bw.newLine();
                        bw.write("Passenger DOB: "+r.getDate("passengerdob"));
                        bw.newLine();
                        bw.write("DiscountPass ID: "+r.getInt("DiscountPassID"));
                        bw.newLine();
                       if(r1.next()){
                        bw.write("DiscountPass Name: "+r1.getString("passname"));
                        bw.newLine();
                       }
                    }
                  }
                  bw.flush();
            }bw.close();
           
           // System.out.println(l);
           // System.out.println(l.size());
            insertTicket(f,l);
            fw=new FileWriter(f,false);
            fw.write("");
            fw.flush();
            fw.close();
            bw.close();
           
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }
        
    }
    public void insertTicket(File f, DataStructure.LinkedList<Integer> tid){
        String sql="update ticket set ticketcontent=? where ticketid=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            for(int i=0;i<tid.size();i++){
                FileReader fr=new FileReader(f);
            PreparedStatement pst=connection.prepareStatement(sql);
            pst.setCharacterStream(1, fr);
            pst.setInt(2, tid.get(i));
            pst.executeUpdate();
           // System.out.println(i);
            fr.close();
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
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
            }else{
                System.out.println("Invalid Trip Id !");
                return false;
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
            String sql = "INSERT INTO ticket (TripID, BookedBy, BookedFor, BookTime, ticketcontent) VALUES (?, ?, ?, NOW(), ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, tripId);
                pstmt.setInt(2, this.id);
                pstmt.setInt(3, selectedPassengerID);
                File f=new File("D:/shivam/temp.txt");
                 
                try {
                    FileReader  fr=new FileReader(f);
                     pstmt.setCharacterStream(4, fr);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                

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
        System.out.println("----------------------------- Booket Ticket History ----------------------");
        int bookedById = this.id;
        Scanner sc=new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // SQL query to get ticket history with joins
            String sql = "SELECT t.ticketcontent, t.TicketID, t.TripID, r.StartLocation, r.EndLocation, " +
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
            boolean b=true;
            while (resultSet.next()) {
            b=false;
                int ticketId = resultSet.getInt("TicketID");
                int tripId = resultSet.getInt("TripID");
                String startLocation = resultSet.getString("StartLocation");
                String endLocation = resultSet.getString("EndLocation");
                Timestamp startTime = resultSet.getTimestamp("StartTime");
                Timestamp endTime = resultSet.getTimestamp("EndTime");
                String bookedByName = resultSet.getString("BookedByName");
                String bookedForName = resultSet.getString("BookedForName");

                
                System.out.println("Ticket ID: " + ticketId+"  From: " + startLocation + " To: " + endLocation+"  Start Time: " + startTime+"  End Time: " + endTime);
               
            }if(b){
            System.out.println("No Tickets Booked Yet !");
            return;}
            System.out.print("Enter Ticket ID to View and Download ticket(Enter 0 to return): ");
            int ch=sc.nextInt();
            sc.nextLine();
            String q1 = "SELECT t.ticketcontent, t.TicketID, t.TripID, r.StartLocation, r.EndLocation, " +
            "tr.StartTime, tr.EndTime, " +
            "c.CustomerName AS BookedByName, " +
            "p.PassengerName AS BookedForName " +
            "FROM Ticket t " +
            "JOIN Trip tr ON t.TripID = tr.TripID " +
            "JOIN Route r ON tr.RouteID = r.RouteID " +
            "JOIN Customer c ON t.BookedBy = c.CustomerID " +
            "JOIN Passenger p ON t.BookedFor = p.PassengerID " +
            "WHERE t.BookedBy = ? and t.ticketid= ?";
            PreparedStatement pt=connection.prepareStatement(q1);
            pt.setInt(1,bookedById);
            pt.setInt(2, ch);
            ResultSet rs=pt.executeQuery();
            if(rs.next()){
                Clob c=rs.getClob("ticketcontent");
                Reader r=c.getCharacterStream();
                BufferedReader br=new BufferedReader(r);
                String s;
                try {
                    s = br.readLine();
                    while(s!=null){
                        System.out.println(s);
                        s=br.readLine();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }System.out.print("Download ?(yes/no): ");
                String choice=sc.next();
                if(choice.equalsIgnoreCase("yes")){
                    System.out.print("Enter File Name you want save your ticket as: ");
                    sc.nextLine();
                    String fn=sc.nextLine();
                    try {
                        FileWriter fw=new FileWriter("D:/shivam/"+fn+".txt");
                        Clob c1=rs.getClob("ticketcontent");
                        Reader r1=c1.getCharacterStream();
                        BufferedReader br1=new BufferedReader(r1);
                        BufferedWriter bw=new BufferedWriter(fw);
                        String s1=br1.readLine();
                        while(s1!=null){
                            bw.write(s1);
                            bw.newLine();
                            bw.flush();
                            s1=br1.readLine();
                        }
                        fw.close();
                        r1.close();
                        br1.close();
                        bw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                }

               

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upcomingJourneys() throws Exception {
        System.out.println("------------------------------- Upcoming Journeys ------------------------------");
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
        // while (ch < 1 || ch >= i) {// this part is causing problems
        //     System.out.println("Enter Valid Ticket ID!");
        //     ch = sc.nextInt();
        // }
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
            System.out.println("---------------------------------- Passenger Details ---------------------------");
            String q="select * from passenger where passengerid=?";
            PreparedStatement ps=connection.prepareStatement(q);
            String g="select passname from discountpass where discountpassid=?";
            PreparedStatement pp=connection.prepareStatement(g);
            
            ps.setInt(1,rs2.getInt("bookedfor"));
            ResultSet r=ps.executeQuery();
            if(r.next()){
                pp.setInt(1,r.getInt("DiscountPassID"));
                ResultSet r1=pp.executeQuery();
                System.out.println("Passenger Name: "+r.getString("passengername"));
                System.out.println("passenger PhoneNumber: "+r.getString("PassengerNumber"));
                System.out.println("Passenger Email: "+r.getString("PassengerEmail"));
                System.out.println("Passenger DOB: "+r.getDate("passengerdob"));
                System.out.println("DiscountPass ID: "+r.getInt("DiscountPassID"));
               if(r1.next()){
                System.out.println("DiscountPass Name: "+r1.getString("passname"));
               }
            }
          }else{
            System.out.println("Invalid Ticket ID!");
          }upcomingJourneys();
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
    @Override
    public void updateName() {
        System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
        String sql = "UPDATE Customer SET CustomerName = ? WHERE CustomerID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, this.getID());
            preparedStatement.executeUpdate();
this.name=newName;
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
            
            System.out.println();
            System.out.println("------------------------- Profile Menu -----------------------");
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
            System.out.println("-------------------------- Personal Details -------------------------");
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
                    
                    updateName();
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
                    String newPassword = setValidPassword();
                    String n1=ip.encryptPassword(newPassword);
                    updatePassword(this.id, n1);
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

        DataStructure.ArrayList<Passenger> passengers = callMethod.getPassengersByCustomerID(this.id);

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
