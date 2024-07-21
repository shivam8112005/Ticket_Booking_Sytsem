import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void main(String[] args) {
       
    }
    private Scanner scanner = new Scanner(System.in);
    private Admin currentAdmin;

    public void signUpMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    this.currentAdmin = new Admin();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void login() {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (Admin.allAdmin.containsKey(username) && Admin.allAdmin.get(username).checkPassword(password,currentAdmin)) {
            System.out.println("Login successful!");
            this.currentAdmin = Admin.allAdmin.get(username);
            adminMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public void adminMenu() {

        while (true) {
            System.out.println("Admin Options:");
            System.out.println("1. Discount Pass Options");
            System.out.println("2. Trip Options");
            System.out.println("3. Bus Options");
            System.out.println("4. Profile");
            System.out.println("5. Logout");
            System.out.println("6. App Statistics");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    discountPassMenu();
                case 2:
                    tripMenu();
                    break;
                case 3:
                    busMenu();
                    break;
                case 4:
                    changeProfile();
                    break;
                case 5:
                    System.out.println("Logged out successfully.");
                    return;
                case 6:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void discountPassMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Discount Pass Menu:");
            System.out.println("1. View All Discount Passes");
            System.out.println("2. Add Discount Pass");
            System.out.println("3. Delete Discount Pass");
            System.out.println("4. Alter Discount Pass");
            System.out.println("5. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    DiscountPass.printAllDiscountPass();
                    break;
                case 2:
                    new DiscountPass();
                    break;
                case 3:
                    DiscountPass.printAllDiscountPass();

                    System.out.print("Enter the Pass ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    DiscountPass passToRemove = null;
                    for (DiscountPass pass : DiscountPass.getAllDiscountPass()) {
                        if (pass.getPassId() == deleteId) {
                            passToRemove = pass;
                            break;
                        }
                    }
                    if (passToRemove != null) {
                        DiscountPass.getAllDiscountPass().remove(passToRemove);
                        System.out.println("Discount Pass removed successfully.");
                    } else {
                        System.out.println("Pass ID not found.");
                    }
                    break;
                case 4:
                    DiscountPass.printAllDiscountPass();
                    System.out.print("Enter the Pass ID to alter: ");
                    int alterId = scanner.nextInt();
                    scanner.nextLine();
                    for (DiscountPass pass : DiscountPass.getAllDiscountPass()) {
                        if (pass.getPassId() == alterId) {
                            boolean alterExit = false;
                            while (!alterExit) {
                                System.out.println("Pass ID: " + pass.getPassId());
                                System.out.println("1. Change Pass Name");
                                System.out.println("2. Change Discount Percentage");
                                System.out.println("3. Return");

                                System.out.print("Enter your choice: ");
                                int alterChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (alterChoice) {
                                    case 1:
                                        try {
                                            pass.setPassName(pass);
                                        } catch (Exception e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        break;
                                    case 2:
                                        pass.setDiscountPercentage();
                                        break;
                                    case 3:
                                        alterExit = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void tripMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Trip Menu:");
            System.out.println("1. View All Trips");
            System.out.println("2. View All Pending Trips");
            System.out.println("3. View All Ended Trips");
            System.out.println("4. Add Trip");
            System.out.println("5. Add Multiple Trips with Fixed Start and End Location");
            System.out.println("6. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    Trip.printAllTrip();
                    break;
                case 2:
                    LocalDateTime currentTime = LocalDateTime.now(); // Get the current time
                    for (Trip trip : Trip.getAllTrip()) {
                        if (trip.getEndTime().isAfter(currentTime)) {
                            System.out.println("Trip ID: " + trip.getTripId() +
                                    ", Start Location: " + trip.getStartLocation() +
                                    ", Start Time: " + trip.getStartTime() +
                                    ", End Location: " + trip.getEndLocation() +
                                    ", End Time: " + trip.getEndTime());
                        }
                    }
                    break;
                case 3:
                    currentTime = LocalDateTime.now(); // Get the current time
                    for (Trip trip : Trip.getAllTrip()) {
                        if (trip.getEndTime().isBefore(currentTime)) {
                            System.out.println("Trip ID: " + trip.getTripId() +
                                    ", Start Location: " + trip.getStartLocation() +
                                    ", Start Time: " + trip.getStartTime() +
                                    ", End Location: " + trip.getEndLocation() +
                                    ", End Time: " + trip.getEndTime());
                        }
                    }
                    break;
                case 4:
                    Trip.allTrip.add(new Trip());
                    break;
                case 5:
                    System.out.print("Enter start location: ");
                    String startLocation = scanner.nextLine();

                    System.out.print("Enter end location: ");
                    String endLocation = scanner.nextLine();

                    int numberOfTrips = 0;
                    while (numberOfTrips <= 0) {
                        System.out.print("How many trips do you want to enter (must be greater than 0): ");
                        numberOfTrips = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    }

                    List<Trip> newTrips = new ArrayList<>(); // List to hold newly created trips

                    for (int i = 0; i < numberOfTrips; i++) {
                        System.out.println("Entering details for trip " + (i + 1) + ":");

                        LocalDateTime startTime = Trip.setTime("Enter start time (format: yyyy-MM-dd HH:mm): ");
                        LocalDateTime endTime = Trip.setTime("Enter end time (format: yyyy-MM-dd HH:mm): ");

                        // Create a new trip with the given details
                        Trip trip = new Trip(startLocation, startTime, endLocation, endTime);
                        newTrips.add(trip); // Add the new trip to the list
                    }

                    // Print only the newly added trips
                    System.out.println("Newly added trips:");
                    for (Trip trip : newTrips) {
                        System.out.println("Trip ID: " + trip.getTripId() +
                                ", Start Location: " + trip.getStartLocation() +
                                ", Start Time: " + trip.getStartTime() +
                                ", End Location: " + trip.getEndLocation() +
                                ", End Time: " + trip.getEndTime());

                    }

                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public void busMenu() {
        while (true) {
            System.out.println("Bus Menu:");
            System.out.println("1. View All Buses");
            System.out.println("2. Add Bus");
            System.out.println("3. Delete Bus");
            System.out.println("4. Alter Bus");
            System.out.println("5. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Bus.printAllBus();
                    break;
                case 2:
                System.out.println("Enter Trip Details for Bus: ");
                Trip t=new Trip();
                    new Bus(t);
                    Trip.allTrip.add(t);
                    break;
                case 3:
                    Bus.printAllBus();
                    System.out.print("Enter the Bus ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    Bus busToRemove = null;
                    for (Bus bus : Bus.getAllBus().keySet()) {
                        if (bus.getBusId() == deleteId) {
                            busToRemove = bus;
                            break;
                        }
                    }
                    if (busToRemove != null) {
                        Bus.getAllBus().remove(busToRemove);
                        System.out.println("Bus removed successfully.");
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                case 4:
                    Bus.printAllBus();
                    System.out.print("Enter the Bus ID to alter: ");
                    int alterId = scanner.nextInt();
                    scanner.nextLine();
                    for (Bus bus : Bus.getAllBus().keySet()) {
                        if (bus.getBusId() == alterId) {
                            boolean alterExit = false;
                            while (!alterExit) {
                                System.out.println("Bus ID: " + bus.getBusId());
                                System.out.println("1. Change Number Plate");
                                System.out.println("2. Change Route");
                                System.out.println("3. Change Bus Seats");
                                System.out.println("4. Return");

                                System.out.print("Enter your choice: ");
                                int alterChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (alterChoice) {
                                    case 1:
                                        bus.setNumberPlate();
                                        break;
                                    case 2:
                                        bus.setRoute();
                                        break;
                                    case 3:
                                        bus.setBusSeats();
                                        break;
                                    case 4:
                                        alterExit = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void changeProfile() {

        while (true) {
            System.out.println("Profile Options:");
            System.out.println("1. View Details");
            System.out.println("2. Change Name");
            System.out.println("3. Change Password");
            System.out.println("4. Change Phone Number");
            System.out.println("5. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Admin ID: " + currentAdmin.getAdminId());
                    System.out.println("Name: " + currentAdmin.getName());
                    System.out.println("Phone Number: " + currentAdmin.getPhoneNumber());
                    break;
                case 2:
                    currentAdmin.setUserName(currentAdmin);
                    break;
                case 3:
                    currentAdmin.setPassword(currentAdmin);
                    break;
                case 4:
                    currentAdmin.setPhoneNumber(currentAdmin);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
