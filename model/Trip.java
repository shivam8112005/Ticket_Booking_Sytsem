import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class Trip {
    // java.sql.Time tripTime = resultSet.getTime("trip_time");
    private static Scanner scanner = new Scanner(System.in);
     static LinkedList<Trip> allTrip = new LinkedList<>();
    static int tripCounter = 1;
    private int tripId;
    private String startLocation;
    private LocalDateTime startTime;
    private String endLocation;
    private LocalDateTime endTime;
    public Trip() {
        this.tripId = tripCounter++;
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
        this.startTime = setTime("Enter start time (format: yyyy-MM-dd HH:mm): ");
        this.endLocation = setLocation("Enter end location (max 64 characters): ");
        this.endTime = setTime("Enter end time (format: yyyy-MM-dd HH:mm): ");
        allTrip.add(this);
    }

    public Trip(String startLocation, LocalDateTime startTime, String endLocation, LocalDateTime endTime) {
        this.tripId = tripCounter++;
        this.startLocation = startLocation;
        this.startTime = startTime;
        this.endLocation = endLocation;
        this.endTime = endTime;
        allTrip.add(this);
    }

    public int getTripId() {
        return this.tripId;
    }

    public String getStartLocation() {
        return this.startLocation;
    }

    public String setLocation(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.length() <= 64) {
                this.startLocation = input;
                return input;
            } else {
                System.out.println("Start location must be 64 characters or less. Please re-enter.");
            }
        }
    }

    public void setStartLocation() {
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
    }

    public static LinkedList<Trip> getAllTrip() {
        return allTrip;
    }

    public static LocalDateTime setTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Pattern for date and time
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, formatter); // Parse input to LocalDateTime
            } catch (Exception e) {
                System.out.println("Invalid date/time format. Please enter in 'yyyy-MM-dd HH:mm' format.");
            }
        }
    }

    public boolean isPending() {
        return this.endTime.isAfter(LocalDateTime.now()); // Check if end time is after current time
    }

    public int getTripCounter() {
        return tripCounter;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime() {
        this.startTime = setTime("Enter start time: ");
    }

    public String getEndLocation() {
        return this.endLocation;
    }

    public void setEndLocation() {
        this.endLocation = setLocation("Enter end location (max 64 characters): ");
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime() {
        this.endTime = setTime("Enter end time: ");
    }

    public String getName() {
        return this.startLocation + " to " + this.endLocation;
    }

    public static void printAllTrip() {
        for (Trip trip : getAllTrip()) {
            System.out.println("Trip ID: " + trip.getTripId() +
                    ", Start Location: " + trip.getStartLocation() +
                    ", Start Time: " + trip.getStartTime() +
                    ", End Location: " + trip.getEndLocation() +
                    ", End Time: " + trip.getEndTime());
        }
    }

    @Override
    public String toString() {
        return "trip{" +
                "tripId=" + tripId +
                ", startLocation='" + startLocation + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
