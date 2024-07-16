import java.util.LinkedList;

class DiscountPass {
    public static LinkedList<DiscountPass> allDP=new LinkedList<>(); 
    private int passId;
    private String passType;
    private double discountPercentage;

    public DiscountPass(int passId, String passType, double discountPercentage) {
        this.passId = passId;
        this.passType = passType;
        this.discountPercentage = discountPercentage;
        allDP.add(this);
    }

    public int getPassId() {
        return passId;
    }

    public void setPassId(int passId) {
        this.passId = passId;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "DiscountPass{" +
                "passId=" + passId +
                ", passType='" + passType + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
