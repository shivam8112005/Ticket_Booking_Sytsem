import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Route {
    // java.sql.Time routeTime = resultSet.getTime("route_time");
    private Scanner scanner = new Scanner(System.in);
    private static LinkedList<Route> allRoute = new LinkedList<>();
    static int routeCounter = 1;
    private int routeId;
    private String startLocation;
    private String startTime;
    private String endLocation;
    private String endTime;
    private boolean[] daysOfWeek; // true if the route runs on that day, false otherwise

    public Route() {
        this.routeId = routeCounter++;
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
        this.startTime = setTime("Enter start time: ");
        this.endLocation = setLocation("Enter end location (max 64 characters): ");
        this.endTime = setTime("Enter end time: ");
        setDaysOfWeek();
        allRoute.add(this);
    }

    public int getRouteId() {
        return this.routeId;
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

    public void setDaysOfWeek() {
        this.daysOfWeek = new boolean[7];
        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        for (int i = 0; i < days.length; i++) {
            while (true) {
                System.out.print("Does the route run on " + days[i] + "? (yes/no): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("yes")) {
                    this.daysOfWeek[i] = true;
                    break;
                } else if (input.equals("no")) {
                    this.daysOfWeek[i] = false;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }
        }
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = setLocation("Enter start location (max 64 characters): ");
    }

    public static LinkedList<Route> getAllRoute() {
        return allRoute;
    }

    public int getRouteCounter() {
        return routeCounter;
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

    public boolean[] getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public String getName() {
        return this.startLocation + " to " + this.endLocation;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", startLocation='" + startLocation + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", endTime='" + endTime + '\'' +
                ", daysOfWeek=" + Arrays.toString(daysOfWeek) +
                '}';
    }
}
