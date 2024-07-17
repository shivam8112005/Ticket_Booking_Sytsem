import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class CustomerMenu extends Customer{
    
    User ul;
    Scanner sc=new Scanner(System.in);
    int choice;
     public CustomerMenu(int a){
        super(10);
     }
    public static void main(String[] args) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Trip t=new Trip("ahmedabad",LocalDateTime.parse("2024-07-17 22:15", formatter
        ),"surat",LocalDateTime.parse("2024-07-18 09:15", formatter
        ));
       Bus bus=new Bus(t);
       bus.getAllBus().put(bus, t);
        CustomerMenu cm=new CustomerMenu(10);
        cm.signUpMenu();
    }
    public  void signUpMenu() {
        boolean exit = true;
        
        while (exit) {
            System.out.println(users);
       
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

    public void customerLogIn(){
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
    private void findUserByEmail(String input) {
        boolean b=false;
        if(users.containsKey(input)){
            b=true;
            if(choice==1){
                System.out.println("Email already exists!");
            }else{
            while(true){
            System.out.println("enter password: ");
            String password=sc.next();
            if(password.equals(users.get(input).getPassword())){
                ul=users.get(input);
                customermenu();
                break;
            }else{
               System.out.println("pasword incorrect!");
               System.out.println("Reset password ?(yes/no): ");
               String choice =sc.next();
               if(choice.equalsIgnoreCase("yes")){
                System.out.println("Enter UserId: ");
                int userid=sc.nextInt();
                if(userid==users.get(input).getUserId()){
                   setPassword();
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
            bookTicket();
                break;
            case 2:
                
                break;
            case 3:
                
                break;  
            case 4:
            System.out.println(ul.ticketBookedHistory);
                break;
            case 5:
            
                break;
            case 6:
             exit=false;
                break;
        }
       }
    }
    public synchronized void  bookTicket(){
        ArrayList<Bus> avlbus=new ArrayList<>();
        int i=1;
        boolean b=true;
       System.out.println("Enter startLocation: ");
       sc.nextLine();
       String start=sc.nextLine();
       System.out.println("Enter EndLocation: ");
       String end=sc.nextLine();
        for (Bus bus : Bus.getAllBus().keySet()) {
           if(Bus.getAllBus().get(bus).getStartLocation().equalsIgnoreCase(start) && Bus.getAllBus().get(bus)
           .getEndLocation().equalsIgnoreCase(end)){
            b=false;
            System.out.println(i++ +". Bus Details: "+bus.toString());
            System.out.println("Trip Details: "+Bus.getAllBus().get(bus).toString());
            System.out.println("--------------------------------------------");
            avlbus.add(bus);
           }
        }if(b){
            System.out.println("no buses availble!");
            return;
        }
        System.out.print("Select Bus By Entering Bus Id: ");
        int c=1;
       while(true){
        c=sc.nextInt();
        if(c>=1 && c<=avlbus.size()){
            break;
        }else{
            System.out.println("please enter valid input!");
        }
       }
        Bus selectedBus=avlbus.get(c-1);
        System.out.println(selectedBus.toString());
        System.out.println("Ticket Price: "+selectedBus.price);
        System.out.println("Book Ticket ? (y/n): ");
        String ch=sc.next();
        if(ch.equalsIgnoreCase("n")){
            return;
        }
        if(ul.usersPassenger.size()==0){
            Passenger passenger=new Passenger();
            ul.usersPassenger.add(passenger);
            System.out.println("Enter Row: ");
            int row=sc.nextInt();
            System.out.println("Enter Column: ");
            int col=sc.nextInt();
           while(! selectedBus.bookSeat(row,col,passenger)){
            System.out.println("Seat not available!");
            System.out.println("Enter Row: ");
             row=sc.nextInt();
            System.out.println("Enter Column: ");
             col=sc.nextInt();
           }
            Ticket t=new Ticket(selectedBus.price, selectedBus, passenger);
            ul.ticketBookedHistory.add(t);
            return;
        }
        System.out.println("Previous Passengers:");
        int p=1;
        
        for(Passenger l:ul.usersPassenger){
            System.out.println(p +". "+l.toString());
            p++;
        }//logic to add multiple passengers at the same time
        System.out.println(p+". New Passenger +");
        int choice=1;
        while(true){
            choice=sc.nextInt();
         if(choice>=1 && choice<=ul.usersPassenger.size()+1){
             break;
         }else{
             System.out.println("please enter valid input!");
         }
        }
        if(choice==ul.usersPassenger.size()+1){
            Passenger passenger=new Passenger();
            ul.usersPassenger.add(passenger);
            System.out.println("Enter Row: ");
            int row=sc.nextInt();
            System.out.println("Enter Column: ");
            int col=sc.nextInt();
           while(! selectedBus.bookSeat(row,col,passenger)){
            System.out.println("Seat not available!");
            System.out.println("Enter Row: ");
             row=sc.nextInt();
            System.out.println("Enter Column: ");
             col=sc.nextInt();
           }
            Ticket t=new Ticket(selectedBus.price, selectedBus, passenger);
            ul.ticketBookedHistory.add(t);
            return;
        }
        System.out.println("Enter Row: ");
        int row=sc.nextInt();
        System.out.println("Enter Column: ");
        int col=sc.nextInt();
       while(! selectedBus.bookSeat(row,col, ul.usersPassenger.get(choice-1))){
        System.out.println("Seat not available!");
        System.out.println("Enter Row: ");
         row=sc.nextInt();
        System.out.println("Enter Column: ");
         col=sc.nextInt();
       }
        Ticket t=new Ticket(selectedBus.price, selectedBus, ul.usersPassenger.get(choice-1));
        ul.ticketBookedHistory.add(t);
    }
}
