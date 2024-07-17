import java.util.Scanner;

public class Passenger extends User {
    Scanner sc=new Scanner(System.in);
    private String gender;
    private int age;
    private static int idCounter = 1;

    public Passenger() {
        super(10);
        setName();
        setAge();
        this.gender = inputGender();
        idCounter++;
    }
    
    
    public void setAge() {
        System.out.println("Enter Age: ");
        int age;
        while(true){
            age=sc.nextInt();
            if(age<=0 || age>=120){
                System.out.println("Enter valid Input!");
            }else{
                break;
            }
        }
        this.age = age;
    }
    // Method to take input for gender
    private String inputGender() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter gender: ");
        return scanner.nextLine();
    }


    public String getGender() {
        return gender;
    }


    public int getAge() {
        return age;
    }


    public static int getIdCounter() {
        return idCounter;
    }


    @Override
    public String toString() {
        return "Passenger [Name=" + getName() +", gender=" + gender + ", age=" + age + ", Passenger Id=" 
        +getIdCounter()+"]";
    }

  
}
