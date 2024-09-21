
package person;

import java.io.File;
import java.sql.Connection;

public interface CustomerInterface {
    
    void profileMenu();
    void bookTicketDisplay();
    void ticketProcessing(int tripId);
    boolean checkSeatAvailability(String tableName,int seatsToBook);
    void updateSeatAvailability(String tableName, int ticketId);
    void writeTicket(DataStructure.LinkedList<Integer> l, Connection connection);
    void insertTicket(File f, DataStructure.LinkedList<Integer> tid);
    void upcomingJourneys() throws Exception;
    void printBookedTicketHistory();
}

