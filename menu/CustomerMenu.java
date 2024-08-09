
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class CustomerMenu extends Customer{
    
    Customer ul;
    Scanner sc=new Scanner(System.in);
    int choice;
     public CustomerMenu(int a){
        super(10);
     }
    public static void main(String[] args) throws Exception{
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
       // Trip t=new Trip("ahmedabad",LocalDateTime.parse("2024-07-17 22:15", formatter
        //),"surat",LocalDateTime.parse("2024-07-18 09:15", formatter
        //));
      // Bus bus=new Bus(t);
       //bus.getAllBus().put(bus, t);
        CustomerMenu cm=new CustomerMenu(10);
        cm.signUpMenu();
    }
    public  void signUpMenu() throws Exception{
        boolean exit = true;
        
        while (exit) {
         System.out.println();
            System.out.println("User Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Return");

            System.out.print("Enter your choice: ");
             choice = sc.nextInt();
            

            switch (choice) {
                case 1:
                System.out.println("Sign Up: ");
                   customerLogIn();
                    break;
                case 2:
                System.out.println("Log In: ");
                   customerLogIn();
                    break;
                case 3:
                    exit = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void customerLogIn() throws Exception{
        while (true) {
            System.out.print("Enter email: ");
            String input = sc.next();
            if (isValidEmail(input)) {
                findUserByEmail(input);
                break;
            }
            System.out.println("Invalid email. Please re-enter the email.");
        }
    }
    private void findUserByEmail(String input) throws Exception{
        boolean b=false;
        if(Customer.getUserByEmail(input)!=null){
            b=true;
            if(choice==1){
                System.out.println("Email already exists!");
            }else{
            while(true){
            System.out.println("enter password: ");
            String password=sc.next();
            if(password.equals(Customer.getUserByEmail(input).getPassword())){
                ul=Customer.getUserByEmail(input);
                customermenu();
                break;
            }else{
               System.out.println("pasword incorrect!");
               System.out.println("Reset password ?(yes/no): ");
               String choice =sc.next();
               if(choice.equalsIgnoreCase("yes")){
                System.out.println("Enter UserId: ");
                int userid=sc.nextInt();
                if(userid==Customer.getUserByEmail(input).getID()){
                   System.out.print("Enter New Password: ");
                   String pas=sc.next();
                   Customer.getUserByEmail(input).setPassword(pas);
                   System.out.println("Password Updated successfully");
                   break;
                }else{
                    System.out.println("incorrect user id! login failed");
                    break;
                }
               }
            }
        }}
        }
       if(!b){
        if(choice==1){
            this.ul=new Customer(input);
            customermenu();
        }
        else{
            System.out.println("User Not found!");
        System.out.println("Don't Have an Account? want to sign up ? (y/n):");
        String ch=sc.next();
        if(ch.equalsIgnoreCase("y")){
            this.ul=new Customer(input);
            customermenu();
        }
        }
       }
    }
    private void customermenu() {
        boolean exit=true;
       while(exit){
        System.out.println("1. Book Ticket");
        System.out.println("2. View Buses");
        System.out.println("3. Upcoming Journeys");
        System.out.println("4. View Booked Ticket History");
        System.out.println("5. Profile");
        System.out.println("6. Return");
        int c=sc.nextInt();
        switch (c) {
            case 1:
                break;
            case 2:
                
                break;
            case 3:
                
                break;  
            case 4:
                break;
            case 5:
            
                break;
            case 6:
             exit=false;
                break;
        }
       }
    }
    
}
