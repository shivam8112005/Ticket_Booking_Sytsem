import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Ticket {

    private int ticketID;
    private int tripID;
    private int bookedBy; // CustomerID
    private int bookedFor; // PassengerID
    private Timestamp bookTime;

    // No-parameter constructor
    public Ticket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Ticket Details");

        System.out.println("Enter Trip ID: ");
        this.tripID = scanner.nextInt();

        System.out.println("Enter Customer ID (Booked By): ");
        this.bookedBy = scanner.nextInt();

        System.out.println("Enter Passenger ID (Booked For): ");
        this.bookedFor = scanner.nextInt();

        // Capture the current timestamp for bookTime
        this.bookTime = new Timestamp(System.currentTimeMillis());

        // Call the method to add the ticket to the database
        addTicketToDB(this.tripID, this.bookedBy, this.bookedFor, this.bookTime);
    }

    // Parameterized constructor
    public Ticket(int ticketID, int tripID, int bookedBy, int bookedFor, Timestamp bookTime) {
        this.ticketID = ticketID;
        this.tripID = tripID;
        this.bookedBy = bookedBy;
        this.bookedFor = bookedFor;
        this.bookTime = bookTime;
    }

    public Ticket(int a) {
        // A constructor that is only to access methods
    }

    // Method to add a ticket to the database
    public void addTicketToDB(int tripID, int bookedBy, int bookedFor, Timestamp bookTime) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "root", "")) {
            String sql = "INSERT INTO Ticket (TripID, BookedBy, BookedFor, BookTime) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tripID);
            preparedStatement.setInt(2, bookedBy);
            preparedStatement.setInt(3, bookedFor);
            preparedStatement.setTimestamp(4, bookTime);

            preparedStatement.executeUpdate();
            System.out.println("Ticket added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a ticket from the database using the TicketID
    public Ticket getTicketFromDB(int ticketID) {
        Ticket ticket = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_booking_db",
                "root", "")) {
            String sql = "SELECT * FROM Ticket WHERE TicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticketID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("TicketID");
                int tripID = resultSet.getInt("TripID");
                int bookedBy = resultSet.getInt("BookedBy");
                int bookedFor = resultSet.getInt("BookedFor");
                Timestamp bookTime = resultSet.getTimestamp("BookTime");

                ticket = new Ticket(id, tripID, bookedBy, bookedFor, bookTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    // Getters and setters
    public int getTicketID() {
        return ticketID;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public int getBookedFor() {
        return bookedFor;
    }

    public void setBookedFor(int bookedFor) {
        this.bookedFor = bookedFor;
    }

    public Timestamp getBookTime() {
        return bookTime;
    }

    public void setBookTime(Timestamp bookTime) {
        this.bookTime = bookTime;
    }
}
