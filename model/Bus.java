import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Bus {
    private int busID;
    private String numberPlate;
    private int numberOfSeats;

    private static final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public Bus() {
        System.out.println("Enter Bus Details:");
        setNumberPlate();
        System.out.print("Enter number of seats: ");
        this.numberOfSeats = scanner.nextInt();
        addBusToDB(numberPlate, numberOfSeats);
    }

    // Parameterized constructor
    public Bus(int busID, String numberPlate, int numberOfSeats) {
        this.busID = busID;
        this.numberPlate = numberPlate;
        this.numberOfSeats = numberOfSeats;
    }

    // Method to set the number plate with validation
    public void setNumberPlate() {
        String platePattern = "^[A-Z]{2}[0-9]{2} [A-Z]{1,2} [0-9]{1,4}$";
        while (true) {
            System.out.print("Enter number plate: ");
            String input = scanner.nextLine();
            if (Pattern.matches(platePattern, input)) {
                this.numberPlate = input;
                break;
            } else {
                System.out.println("Invalid number plate format. Please re-enter.");
            }
        }
    }

    // Method to add the bus details to the database
    public void addBusToDB(String numberPlate, int numberOfSeats) {
        String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
        String user = "root";
        String password = "";

        String query = "INSERT INTO Bus (NumberPlate, NumberOfSeats) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, numberPlate);
            stmt.setInt(2, numberOfSeats);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.busID = rs.getInt(1);
                System.out.println("Bus added with ID: " + this.busID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a Bus object from the database using the BusID
    public static Bus getBusFromDB(int busID) {
        String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM Bus WHERE BusID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String numberPlate = rs.getString("NumberPlate");
                int numberOfSeats = rs.getInt("NumberOfSeats");
                return new Bus(busID, numberPlate, numberOfSeats);
            } else {
                System.out.println("No bus found with ID: " + busID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters
    public void setBusID(int busID) {
        this.busID = busID;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getBusID() {
        return busID;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
