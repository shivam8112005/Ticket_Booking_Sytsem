import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Trip {
    // java.sql.Time tripTime = resultSet.getTime("trip_time");
    private Scanner scanner = new Scanner(System.in);
    private static LinkedList<Trip> allTrip = new LinkedList<>();
    static int tripCounter = 1;
    private int tripId;
    private String startLocation;
    private String startTime; //Time means time + date as in "13:00 04-January-2024"
    private String endLocation;
    private String endTime;

    public Trip() {
        this.tripId = tripCounter++;
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
        this.startTime = setTime("Enter start time: ");
        this.endLocation = setLocation("Enter end location (max 64 characters): ");
        this.endTime = setTime("Enter end time: ");
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

    public void setStartLocation(String startLocation) {
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
    }

    public static LinkedList<Trip> getAllTrip() {
        return allTrip;
    }

    public int getTripCounter() {
        return tripCounter;
    }

    public String setTime(String prompt) {
        String timePattern = "^([01]?\\d|2[0-3]):[0-5]\\d$"; // Regex pattern for HH:MM format

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (Pattern.matches(timePattern, input)) {
                return input;
            } else {
                System.out.println("Invalid time format. Please enter time in HH:MM format.");
            }
        }
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = setTime("Enter start time: ");
    }

    public String getEndLocation() {
        return this.endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = setLocation("Enter end location (max 64 characters): ");
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = setTime("Enter end time: ");
    }

    public String getName() {
        return this.startLocation + " to " + this.endLocation;
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
