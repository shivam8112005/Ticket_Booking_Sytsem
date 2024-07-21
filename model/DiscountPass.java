import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.Scanner;

public class DiscountPass {
    private Scanner scanner = new Scanner(System.in);
    public static LinkedList<DiscountPass> allDiscountPass = new LinkedList<>();
    private int passIdCounter = 1;

    private int passId;
    private String passName;
    private float discountPercentage;

    public DiscountPass() {
        boolean b=false;
        try {
            b= setPassName(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(b){
        setDiscountPercentage();
        try {
            DatabaseUtil.insertDiscountPass(this.passName,this.discountPercentage);
            setPassId();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        allDiscountPass.add(this);
    }else{
        System.out.println("Pass Not Added !");
    }
    }
    public void setPassId() throws Exception{
        String querry="SELECT passid FROM discountpass WHERE passname = ?";
        PreparedStatement pst=DatabaseUtil.getConnection().prepareStatement(querry);
        pst.setString(1, this.passName);
        ResultSet rs=pst.executeQuery();
        if(rs.next()){
            this.passId=rs.getInt("passid");
        }
    }

    public boolean setPassName(DiscountPass dp) throws Exception{
        String name;
String querry="SELECT passname FROM discountpass WHERE passname= ?";
PreparedStatement pst=DatabaseUtil.getConnection().prepareStatement(querry);
        while (true) {
            System.out.print("Enter name (32 characters or less): ");
            name = scanner.nextLine();

            if (name.length() <= 32) {
                break;
            } else {
                System.out.println("Name must be 32 characters or less. Please re-enter the name.");
            }
        }
        pst.setString(1, name);
        ResultSet rs=pst.executeQuery();
        if(rs.next()){
            System.out.println("Pass Not addedd ! pass name already exists.");
            return false;
        }else{
            dp.passName = name;
            return true;
        }

       
    }

    public void setDiscountPercentage() {
        while (true) {
            System.out.print("Enter discount percentage (0-100): ");
            float discountPercentage = scanner.nextFloat();
            scanner.nextLine(); // Clear the buffer

            if (discountPercentage >= 0 && discountPercentage <= 100) {
                this.discountPercentage = discountPercentage;
                System.out.println("Discount percentage set successfully!");
                break;
            } else {
                System.out.println("Invalid discount percentage. It must be between 0 and 100.");
            }
        }
    }

    public static void printAllDiscountPass() {
        for (DiscountPass pass : DiscountPass.getAllDiscountPass()) {
            System.out.println("Pass ID: " + pass.getPassId());
            System.out.println("Pass Name: " + pass.getPassName());
            System.out.println("Discount Percentage: " + pass.getDiscountPercentage() + "%");
            System.out.println();
        }
    }

    public int getPassId() {
        return passId;
    }

    public static LinkedList<DiscountPass> getAllDiscountPass() {
        return allDiscountPass;
    }

    public int getPassIdCounter() {
        return passIdCounter;
    }

    public String getPassName() {
        return passName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public Scanner getScanner() {
        return scanner;
    }
    public String getPasssName(){
        return this.passName;
    }

    @Override
    public String toString() {
        return "DiscountPass{" +
                "passId=" + passId +
                ", passType='" + passName + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
