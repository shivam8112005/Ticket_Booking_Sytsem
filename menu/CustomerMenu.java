import java.util.*;
public class CustomerMenu extends Customer{
    Scanner sc=new Scanner(System.in);
    public void customerLogIn(){
        while (true) {
            System.out.print("Enter email: ");
            String input = sc.nextLine();
            if (isValidEmail(input)) {
                findUserByEmail(input);
                break;
            }
            System.out.println("Invalid email. Please re-enter the email.");
        }
    }
    private void findUserByEmail(String input) {
        boolean b=false;
       for(User u:users){
        if(u.getEmail().equals(input)){
            b=true;
            while(true){
            System.out.println("enter password: ");
            String password=sc.next();
            if(password.equals(u.getPassword())){
                User ul=u;
                customermenu();
                break;
            }else{
               System.out.println("pasword incorrect!");
               System.out.println("Reset password ?(yes/no): ");
               String choice =sc.next();
               if(choice.equalsIgnoreCase("yes")){
                System.out.println("Enter UserId: ");
                int userid=sc.nextInt();
                if(userid==u.getUserId()){
                   setPassword();
                }else{
                    System.out.println("incorrect user id! login failed");
                }
               }
            }
        }
        }
       }if(!b){
        User us=new Customer();
        users.add(us);
       }
    }
    private void customermenu() {
        
    }
    
    
}
