import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin {
    private Scanner scanner = new Scanner(System.in);

    private String username;
    private String password;
    private String phoneNumber;

    static HashMap<String, Admin> allAdmin = new HashMap<>();

    public Admin() {

        setName();
        setPassword();
        setPhoneNumber();
        allAdmin.put(username, this);
    }

    public String getName() {
        return username;
    }

    public Boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName() {
        String name;

        while (true) {
            System.out.print("Enter name (32 characters or less): ");
            name = scanner.nextLine();

            if (name.length() <= 32) {
                break;
            } else {
                System.out.println("Name must be 32 characters or less. Please re-enter the name.");
            }
        }

        this.username = name;
    }

    public void setPhoneNumber() {

        while (true) {
            System.out.print("Enter phone number: ");
            this.phoneNumber = scanner.nextLine();

            if (isValidPhoneNumber(phoneNumber)) {
                break;
            } else {
                System.out.println("Invalid phone number. Please re-enter the phone number.");
            }
        }

        System.out.println("Phone number set successfully!");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[0-9]{10}$";
        return Pattern.matches(phonePattern, phoneNumber);
    }

    public String setPassword() {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        // Password must contain at least one digit, one lowercase,
        // one uppercase letter, and be at least 8 characters long
        while (true) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (Pattern.matches(regex, password)) {
                return password;
            } else {
                System.out.println(
                        "Invalid password. Password must be at least 8 characters long, contain at least one digit, one lowercase letter, and one uppercase letter.");
                System.out.println();
            }
        }
    }
}
