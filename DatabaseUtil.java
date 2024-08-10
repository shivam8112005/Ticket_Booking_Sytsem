import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertAdmin(String userName, String password, String phoneNumber) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call addAdmin(?,?,?)}");
            cst.setString(1, userName);
            cst.setString(2, password);
            cst.setString(3, phoneNumber);
            int r = cst.executeUpdate();
            System.out.println(r > 0 ? "added succesfullly" : "Not added");
        } catch (Exception e) {
            System.out.println("Exception Thrown" + e);
            return;
        }
    }

    public static boolean checkAdmin(String userName, String pass) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call checkAdmin(?,?)}");
            cst.setString(1, userName);
            cst.setString(2, pass);
            ResultSet rs = cst.executeQuery();
            if (rs == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception aave che" + e);
            return false;
        }
    }

    public static void changeName(int id, String name) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call updateAdminUsername(?,?)}");
            cst.setInt(1, id);
            cst.setString(2, name);
            int r = cst.executeUpdate();
            System.out.println(r > 0 ? "changed succesfullly" : "Not changed");
        } catch (Exception e) {
            System.out.println("Exception Thrown+++" + e);
        }
    }

    public static void changePassword(int id, String password) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call updateAdminPassword(?,?)}");
            cst.setInt(1, id);
            cst.setString(2, password);
            int r = cst.executeUpdate();
            System.out.println(r > 0 ? "changed succesfullly" : "Not changed");
        } catch (Exception e) {
            System.out.println("Exception Thrown+++" + e);
        }
    }

    public static void changePhone(int id, String phoneNumber) {
        try {
            Connection con = getConnection();
            CallableStatement cst = con.prepareCall("{call updateAdminPhone(?,?)}");
            cst.setInt(1, id);
            cst.setString(2, phoneNumber);
            int r = cst.executeUpdate();
            System.out.println(r > 0 ? "changed succesfullly" : "Not changed");
        } catch (Exception e) {
            System.out.println("Exception Thrown+++" + e);
        }
    }
    public static void deleteDiscountPass(int id){
        try{
            String querry="DELETE FROM discountpass WHERE passid = ?";
            PreparedStatement pst=getConnection().prepareStatement(querry);
            pst.setInt(1, id);
            pst.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // public static void setDiscPass() throws Exception {
    //     LinkedList<DiscountPass> discountPasses = new LinkedList<>();
    //     Connection con = getConnection();
    //     Statement st = con.createStatement();
    //     ResultSet rs = st.executeQuery("select * from discountpass");
    //     while (rs.next()) {
    //         DiscountPass discountPass = new DiscountPass(rs.getInt(1), rs.getString(2), rs.getFloat(3));
    //         System.out.println("Added");
    //         discountPasses.add(discountPass);
    //     }

    // }
    
    public static void insertDiscountPass(String passname,float discountpercent) throws Exception{
        String querry="INSERT INTO discountpass (passname, discountpercent) VALUES (?,?)";
        PreparedStatement pst=getConnection().prepareStatement(querry);
        pst.setString(1, passname);
        pst.setFloat(2, discountpercent);
        int r=pst.executeUpdate();
        if(r>0){
            System.out.println("pass Added Successfully .");
        }
    }
    public static void alterDiscountPassName(String name,int passid){
        try{
            String querry="UPDATE discountpass SET passname = ? WHERE passid = ?";
        PreparedStatement pst=getConnection().prepareStatement(querry);
        pst.setString(1, name);
        pst.setInt(2, passid);
        pst.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void alterDiscountPasspercent(float percent, int id){
        try{
            String querry="UPDATE discountpass SET discountpercent = ? WHERE passid = ?";
        PreparedStatement pst=getConnection().prepareStatement(querry);
        pst.setFloat(1, percent);
        pst.setInt(2, id);
        pst.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    // // try {
    // Connection con = getConnection();
    // CallableStatement cst = con.prepareCall("{call viewDiscountPass(?,?,?)}");
    // // You need to set the input parameters for the stored procedure
    // // cst.setInt(1, /* input parameter 1 */);
    // // cst.setString(2, /* input parameter 2 */);
    // // cst.setFloat(3, /* input parameter 3 */);
    // ResultSet rs = cst.executeQuery();
    // while (rs.next()) {
    // int id = rs.getInt(1);
    // String name = rs.getString(2);
    // float disc = rs.getFloat(3);
    // DiscountPass discountPass = new DiscountPass(id, name, disc);
    // discountPasses.add(discountPass);
    // }
    // System.out.println("Discount Passes Viewed");
    // // } catch (Exception e) {
    // // System.out.println("Exception Thrown+++" + e);
    // // }
    // return discountPasses;

   // public static void
}
