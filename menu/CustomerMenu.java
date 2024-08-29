package menu;

import java.util.*;

import model.Trip;
import person.Customer;

public class CustomerMenu {

    Customer currentCustomer;
    Scanner sc = new Scanner(System.in);
    int choice;

    public CustomerMenu(int a) {
        // A constructor that is only to access methods
    }

    public static void main(String[] args) throws Exception {
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Trip t=new Trip("ahmedabad",LocalDateTime.parse("2024-07-17 22:15", formatter
        // ),"surat",LocalDateTime.parse("2024-07-18 09:15", formatter
        // ));
        // Bus bus=new Bus(t);
        // bus.getAllBus().put(bus, t);
        // CustomerMenu cm = new CustomerMenu(10);
        // cm.signUpMenu();
    }

    public void signUpMenu() throws Exception { 
        boolean exit = true;

        while (exit) {
            System.out.println("----------------------------- User Menu -----------------------------");
            
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Return");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    this.currentCustomer = new Customer();
                    customerMenu();
                    break;
                case 2:
                    customerLogIn();
                    customerMenu();

                    break;
                case 3:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void customerLogIn() throws Exception {
            System.out.println("------------------------------- Log In -------------------------------");
            System.out.print("Enter email: ");
            String email = sc.next();
            System.out.print("Enter password: ");
            String phone = sc.next();
            this.currentCustomer = new Customer(email,phone);
    }

    private void customerMenu() {
        boolean exit = true;
        while (exit) {
            System.out.println("----------------------------- Ticket Booking System ----------------------");
            System.out.println("1. Book Ticket");
            System.out.println("2. View Trips");
            System.out.println("3. Upcoming Journeys");
            System.out.println("4. View Booked Tickets");
            System.out.println("5. Profile");
            System.out.println("6. Return");
            System.out.print("Enter you choice: ");
            int c = sc.nextInt();
            sc.nextLine();
            switch (c) {
                case 1:
                    try {
                     //  Trip trip = new Trip();
                        currentCustomer.bookTicketDisplay();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Trip trip = new Trip(0);
                    trip.printUpcomingTrips();
                    break;
                case 3:
                    try {
                        currentCustomer.upcomingJourneys();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        currentCustomer.printBookedTicketHistory();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    currentCustomer.profileMenu();
                    break;
                case 6:
                    exit = false;
                    break;
            }
        }
    }


}
