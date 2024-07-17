import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

abstract class User {
    static int userId = 0;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private DiscountPass dp;
    private HashMap<Integer, Ticket> ticketBookedHistory = new HashMap<>();;
    static HashMap<String,User> users=new HashMap<>();
     public User(int a){
        
     }
    public User(String email) {
        System.out.println("sign up:");
        setName();
        setPhoneNumber();
        setDateOfBirth();
        setDiscountPass();
        setEmail(email);
        setPassword();
        users.put(this.email,this);
        userId++;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", dateOfBirth="
                + dateOfBirth + ", dp=" + dp + ", userId="+userId+ "]";
    }
    // Getters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public DiscountPass getDp() {
        return dp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public static int getUserId() {
        return userId;
    }

    public void setPassword() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter password (minimum 8 characters, must include letters and digits): ");
            String input = scanner.nextLine();
            if (isValidPassword(input)) {
                this.password = input;
                break;
            }
            System.out.println("Invalid password. Please re-enter the password.");
        }
    }
    public String getPassword(){
        return this.password;
    }

   

    public HashMap<Integer, Ticket> getTicketBookedHistory() {
        return ticketBookedHistory;
    }

    public void setTicketBookedHistory(HashMap<Integer, Ticket> ticketBookedHistory) {
        this.ticketBookedHistory = ticketBookedHistory;
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
    public void setName() {
        System.out.println("Enter Name: ");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        while (name.length() > 32) {
            System.out.println("Name must be 32 characters or less. Please re-enter the name:");
            name = scanner.nextLine();
        }
        
    }

    public void setPhoneNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Phone no.: ");
        phoneNumber = scanner.nextLine();
        while (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Invalid phone number. Please re-enter the phone number:");
            phoneNumber = scanner.nextLine();
        }
       
    }

    public void setDateOfBirth() {
        System.out.println("Enter DOB : ");
        Scanner scanner = new Scanner(System.in);
            dateOfBirth = scanner.nextLine();
        while (!isValidDateOfBirth(dateOfBirth)) {
            System.out.println("Invalid date of birth. Please re-enter the date of birth (format: YYYY-MM-DD):");
            dateOfBirth = scanner.nextLine();
        }
       
    }
    public void setEmail() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter email: ");
            String input = scanner.nextLine();
            if (isValidEmail(input)) {
                this.email = input;
                break;
            }
            System.out.println("Invalid email. Please re-enter the email.");
        }
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
    public boolean isValidEmail(String email) {
        // Simple validation to check the email format contains "@" and "."
        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');
        return atPosition > 0 && dotPosition > atPosition;
    }
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain both letters and digits
        if (password.length() < 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        return false;
    }

    // Methods to add tickets and discount passes
    public void addTicket(Ticket ticket) {
        this.ticketBookedHistory.put(ticket.getTicketId(), ticket);
    }
    public void setDiscountPass(){
      
            Scanner scanner = new Scanner(System.in);
            System.out.println("Available Discount Passes:");
            int i = 1;
            for (DiscountPass dp : DiscountPass.allDiscountPass) {
                System.out.println(i + ". " + dp.getPassName());
                i++;
            }
            System.out.println("0. Set DiscountPass to null");
            System.out.print("Select a DiscountPass by entering the corresponding number: ");
            
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 0 || choice > DiscountPass.allDiscountPass.size()) {
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
                    this.dp = DiscountPass.allDiscountPass.get(choice - 1);
                    break;
            }
        
    }

    
}
