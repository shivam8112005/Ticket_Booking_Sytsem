import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1. Log in as Customer");
            System.out.println("2. Log in as Admin");
            System.out.println("3. Close the program");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Customer.customerLogIn();
                    break;
                case 2:
                    AdminMenu.adminLoginPage();
                    break;
                case 3:
                    System.out.println("Closing the program");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}