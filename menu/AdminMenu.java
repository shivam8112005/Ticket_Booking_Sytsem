package menu;

import java.util.HashSet;
import java.util.Scanner;

import DataStructure.InputValidator;
import model.Bus;
import model.DiscountPass;
import model.Trip;
import person.Admin;

public class AdminMenu {
    // public static void main(String[] args) {
    // AdminMenu menu = new AdminMenu(0);
    // menu.login();
    // }

    public AdminMenu(Admin admin) {
        this.currentAdmin = admin;
    }

    public AdminMenu(int a) {
        // to only access method
    }

    private Scanner scanner = new Scanner(System.in);
    private InputValidator iv = new InputValidator();
    private Admin currentAdmin;

    // public void signUpMenu() {
    // boolean exit = false;

    // while (!exit) {
    // System.out.println("Admin Menu:");
    // System.out.println("1. Sign Up");
    // System.out.println("2. Login");
    // System.out.println("3. Return to home page");

    // System.out.print("Enter your choice: ");
    // int choice = scanner.nextInt();

    // switch (choice) {
    // case 1:
    // this.currentAdmin = new Admin();
    // break;
    // case 2:
    // login();
    // break;
    // case 3:
    // exit = true;
    // break;
    // default:
    // System.out.println("Invalid choice. Please try again.");
    // }
    // }
    // }

    public void login() {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.next();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        this.currentAdmin = new Admin(username, password);

    }

    public void adminMenu() {
        while (true) {
            System.out.println();
            System.out.println("------------------------------- Admin Options ---------------------------");
            // Special functionality for root admin to
            // add other admins
            Thread t=new Thread(this.currentAdmin);
         //   System.out.println("hjfewubfuebwufe 111111111111111");
            t.start();
         //   System.out.println("shegfuewufh 33333333333333333");
            if (currentAdmin.getName().equals("root")) {

                System.out.println("0. Add Admin");
            }
            System.out.println("1. Discount Pass Options");
            System.out.println("2. Trip Options");
            System.out.println("3. Bus Options");
            System.out.println("4. Profile");
            System.out.println("5. Logout");

            
            int choice = iv.getIntInput("Enter your choice: ",1,5);
            scanner.nextLine();

            switch (choice) {
                case 0:
                    if (currentAdmin.getName().equals("root")) {
                        System.out.println("Enter Details for new Admin:");
                        new Admin(this.currentAdmin);
                    } else {
                        System.out.println("Only the root can perform this action!");
                    }
                    break;
                case 1:
                    discountPassMenu();
                    break;
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
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void discountPassMenu() {
        boolean exit = false;
        DiscountPass dp = new DiscountPass(0);
        while (!exit) {
            System.out.println();
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
                    dp.printAllDiscountPasses();
                    break;
                case 2:
                    new DiscountPass();
                    break;
                case 3:
                    dp.printAllDiscountPasses();
                    HashSet<Integer> hsDelete = dp.getAllDiscountPassIDs();

                    System.out.print("Enter the Pass ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    if (hsDelete.contains(deleteId)) {
                        dp.deleteDiscountPassFromDB(deleteId);
                        System.out.println("Deletion Successful");
                    } else {
                        System.out.println("No such ID found!");
                    }
                    break;
                case 4:
                    dp.printAllDiscountPasses();
                    HashSet<Integer> hsUpdate = dp.getAllDiscountPassIDs();

                    System.out.print("Enter the Pass ID to alter: ");
                    int alterId = scanner.nextInt();
                    scanner.nextLine();

                    if (hsUpdate.contains(alterId)) {
                        boolean alterExit = false;
                        while (!alterExit) {
                            DiscountPass alterPass = dp.getDiscountPassFromDB(alterId);
                            System.out.println(alterPass);
                            System.out.println("1. Change Pass Name");
                            System.out.println("2. Change Discount Percentage");
                            System.out.println("3. Return");

                            System.out.print("Enter your choice: ");
                            int alterChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (alterChoice) {
                                case 1:

                                    System.out.print("Enter new name: ");
                                    dp.updateNameInDB(alterId, scanner.nextLine());

                                    break;
                                case 2:

                                    System.out.print("Enter new percentage: ");
                                    dp.updateDiscountPercentageInDB(alterId, scanner.nextFloat());

                                    break;
                                case 3:
                                    alterExit = true;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("No such ID found!");
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
        Trip trip = new Trip(0);
        while (!exit) {
            System.out.println();
            System.out.println("Trip Menu:");
            System.out.println("1. View All Trips");
            System.out.println("2. View All Ongoing Trips");
            System.out.println("3. View All Upcoming Trips");
            System.out.println("4. Add Trip");
            System.out.println("5. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    trip.printAllTrips();
                    break;
                case 2:
                    trip.printOngoingTrips();
                    break;
                case 3:
                    trip.printUpcomingTrips();
                    break;
                case 4:
                    new Trip();
                    break;

                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void busMenu() {
        boolean exit = false;
        Bus bus = new Bus(0);
        while (!exit) {
            System.out.println("Bus Menu:");
            System.out.println("1. View All Buses");
            System.out.println("2. Add Bus");
            System.out.println("3. Delete Bus");
            System.out.println("4. Update Bus");
            System.out.println("5. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    bus.printAllBuses();
                    break;
                case 2:
                    new Bus();
                    break;
                case 3:
                    bus.printAllBuses();
                    HashSet<Integer> hsDelete = bus.getAllBusIDs();

                    System.out.print("Enter the Bus ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (hsDelete.contains(deleteId)) {
                        bus.deleteBusByID(deleteId);
                        System.out.println("Deletion Successful");
                    } else {
                        System.out.println("No such ID found!");
                    }
                    break;
                case 4:
                    bus.printAllBuses();
                    HashSet<Integer> hsUpdate = bus.getAllBusIDs();

                    System.out.print("Enter the Bus ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (hsUpdate.contains(updateId)) {
                        boolean updateExit = false;
                        while (!updateExit) {
                            Bus updateBus = bus.getBusFromDB(updateId);
                            System.out.println(updateBus);
                            System.out.println("1. Change Bus Number Plate");
                            System.out.println("2. Change Bus Capacity");
                            System.out.println("3. Return");

                            System.out.print("Enter your choice: ");
                            int updateChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (updateChoice) {
                                case 1:

                                    System.out.print("Enter new Number Plate: ");
                                    updateBus.updateNumberPlate(scanner.nextLine());

                                    break;
                                case 2:
                                    System.out.print("Enter new Capacity: ");
                                    updateBus.updateNumberOfSeats(scanner.nextInt());

                                    break;
                                case 3:
                                    updateExit = true;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("No such ID found!");
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

    public void changeProfile() {
        if (currentAdmin == null) {
            System.out.println("Please login first.");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("Profile Menu:");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.println("3. Return");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (currentAdmin.getAdminId() > 0) {
                        currentAdmin.updateName();
                    } else {
                        System.out.println("Invalid Admin ID.");
                    }
                    break;
                case 2:
                    if (currentAdmin.getAdminId() > 0) {
                        currentAdmin.updatePassword(currentAdmin.getAdminId());
                    } else {
                        System.out.println("Invalid Admin ID.");
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
}
