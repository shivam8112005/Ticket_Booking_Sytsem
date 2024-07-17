import java.util.LinkedList;
import java.util.Scanner;

class DiscountPass {
    private Scanner scanner = new Scanner(System.in);
    public static LinkedList<DiscountPass> allDiscountPass = new LinkedList<>();
    private int passIdCounter = 1;

    private int passId;
    private String passName;
    private float discountPercentage;

    public DiscountPass() {
        this.passId = passIdCounter++;
        setPassName();
        this.discountPercentage = discountPercentage;
        allDiscountPass.add(this);
    }

    public void setPassName() {
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

        this.passName = name;
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

    @Override
    public String toString() {
        return "DiscountPass{" +
                "passId=" + passId +
                ", passType='" + passName + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
