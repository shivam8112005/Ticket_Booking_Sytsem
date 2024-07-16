import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Bus {
    private Scanner scanner = new Scanner(System.in);
    private static LinkedList<Bus> allBus = new LinkedList<>();
    static int busCounter = 1;

    private int busId;
    private String numberPlate;
    private Route route;

    public Bus() {
        this.busId = busCounter++;
        setNumberPlate();
        setRoute();
        allBus.add(this);
    }

    public int getBusId() {
        return this.busId;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

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

    public void setRoute() {
        while (true) {
            System.out.println("Available routes:");
            for (Route route : Route.getAllRoute()) {
                System.out.println(route.getRouteId() + ": " + route.getName());
            }
            System.out.println("0: Add new route");

            System.out.print("Select a route by entering the route ID: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                this.route = new Route();
                break;
            } else {
                boolean found = false;
                for (Route route : Route.getAllRoute()) {
                    if (route.getRouteId() == choice) {
                        this.route = route;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                } else {
                    System.out.println("Invalid route ID. Please try again.");
                }
            }
        }
    }

    public static LinkedList<Bus> getAllBus() {
        return allBus;
    }

    public static int getBusCounter() {
        return busCounter;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", numberPlate='" + numberPlate + '\'' +
                ", route=" + route.getName() +
                '}';
    }
}
