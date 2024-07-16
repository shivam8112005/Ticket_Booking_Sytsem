import java.util.Scanner;

public class AdminMenu {
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

        if (Admin.allAdmin.containsKey(username) && Admin.allAdmin.get(username).checkPassword(password)) {
            System.out.println("Login successful!");
            this.currentAdmin = Admin.allAdmin.get(username);
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
                    break;
                case 2:
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
                    return;
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
                    new Bus();
                    break;
                case 3:
                    Bus.printAllBus();
                    System.out.print("Enter the Bus ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    Bus busToRemove = null;
                    for (Bus bus : Bus.getAllBus()) {
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
                    for (Bus bus : Bus.getAllBus()) {
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
                    currentAdmin.setName();
                    break;
                case 3:
                    currentAdmin.setPassword();
                    break;
                case 4:
                    currentAdmin.setPhoneNumber();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
