import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCleanupTask extends Thread {

    private static final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    public void run() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                try (Statement statement = connection.createStatement()) {
                    // Query to find trips that have ended
                    String query = "SELECT TripID FROM Trip WHERE StartTime < NOW()";
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        while (resultSet.next()) {
                            int tripID = resultSet.getInt("TripID");
                            String tableName = "tripseat_" + tripID;

                            // Call the synchronized method to delete the table
                            deleteTable(connection, tableName);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Sleep for a while before checking again
                try {
                    Thread.sleep(60000); // Sleep for 1 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break; // Exit the loop if interrupted
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Synchronized method to delete a table
    private synchronized void deleteTable(Connection connection, String tableName) {
        try (Statement statement = connection.createStatement()) {
            String deleteTableQuery = "DROP TABLE IF EXISTS " + tableName;
            int rowsAffected = statement.executeUpdate(deleteTableQuery);
    
            if (rowsAffected == 0) {
                // No rows were affected, which means the table did not exist or was already deleted
            } else {
                // Rows were affected, which means the table was successfully deleted
                System.out.println("Successfully deleted table: " + tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
