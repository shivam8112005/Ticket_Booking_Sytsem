import java.util.HashMap;
import java.util.LinkedList;


public class Customer extends User {
    private String password;
     static int customerId = 1; // Static counter for unique IDs
    private LinkedList<Ticket> boookedTicket=new LinkedList<>();
   
    public Customer(int a){
        super(a);
    }
    public Customer(String email) {
        super(email);
    } 
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                '}';
    }
}
