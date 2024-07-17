import java.util.*;
public class CustomerMenu extends Customer{
    User ul;
    Scanner sc=new Scanner(System.in);
    int choice;
     public CustomerMenu(int a){
        super(10);
     }
    public static void main(String[] args) {
       
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
                   customerLogIn();
                    break;
                case 2:
                   customerLogIn();
                    break;
                case 3:
                    exit = true;
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
        
    }
    
    
}
