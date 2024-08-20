import java.util.Scanner;
import menu.AdminMenu;
import menu.CustomerMenu;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Start the table cleanup thread
        TableCleanupTask cleanupTask = new TableCleanupTask();
        cleanupTask.start();

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
                    CustomerMenu cm = new CustomerMenu(0);
                    try {
                        cm.signUpMenu();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    AdminMenu am = new AdminMenu(0);
                    am.login();
                    break;
                case 3:
                    System.out.println("Closing the program");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
