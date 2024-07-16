import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

abstract class User {
    private String name;
    private String phoneNumber;
    private String dateOfBirth;
    private DiscountPass dp;

    private HashMap<Integer, Ticket> ticketBookedHistory = new HashMap<>();;

    public User() {
        System.out.println("sign up:");
        setName(name);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(dateOfBirth);
        setDiscountPass();

    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HashMap<Integer, Ticket> getticketBookedHistory() {
        return ticketBookedHistory;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public DiscountPass getDiscountPass() {
        return this.dp;
    }

    // Setters with validation
    public void setName(String name) {
        while (name.length() > 32) {
            System.out.println("Name must be 32 characters or less. Please re-enter the name:");
            Scanner scanner = new Scanner(System.in);
            name = scanner.nextLine();
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please re-enter the phone number:");
            Scanner scanner = new Scanner(System.in);
            phoneNumber = scanner.nextLine();
        }
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        while (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("Invalid date of birth. Please re-enter the date of birth (format: YYYY-MM-DD):");
            Scanner scanner = new Scanner(System.in);
            dateOfBirth = scanner.nextLine();
        }
        this.dateOfBirth = dateOfBirth;
    }

    // Validation methods
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Simple regex for phone number validation
        return Pattern.matches("^\\+?[0-9]{10,15}$", phoneNumber);
    }

    private boolean isValidDateOfBirth(String dateOfBirth) {
        // Simple regex for date of birth validation (format: YYYY-MM-DD)
        return Pattern.matches("^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", dateOfBirth);
    }

    // Methods to add tickets and discount passes
    public void addTicket(Ticket ticket) {
        this.ticketBookedHistory.put(ticket.getTicketId(), ticket);
    }
    public void setDiscountPass(){
      
            Scanner scanner = new Scanner(System.in);
            System.out.println("Available Discount Passes:");
            int i = 1;
            for (DiscountPass dp : DiscountPass.allDP) {
                System.out.println(i + ". " + dp);
                i++;
            }
            System.out.println("0. Set DiscountPass to null");
            System.out.print("Select a DiscountPass by entering the corresponding number: ");
            
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 0 || choice > DiscountPass.allDP.size()) {
                        System.out.print("Invalid choice. Please select a valid number: ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a number: ");
                }
            }
            
            switch (choice) {
                case 0:
                    this.dp = null;
                    break;
                default:
                    this.dp = DiscountPass.allDP.get(choice - 1);
                    break;
            }
        
    }
}
