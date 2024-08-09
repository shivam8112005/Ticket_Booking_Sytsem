import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin {
    private Scanner scanner = new Scanner(System.in);

    private static int adminCounter = 1;

    private int adminId;
    private String username;
    private String password;
    private String phoneNumber;
    private static AdminMenu am = new AdminMenu();
    static HashMap<String, Admin> allAdmin = new HashMap<>();
    // Here we used Hashmap instead of LL because even though HM are faster O(1)
    // they take a lot of storage which is why we used on admin not costumer or
    // passenger

    public Admin() {
        boolean b = setUserName(this);
        if (b) {
            setPassword(this);
            setPhoneNumber(this);
            DatabaseUtil.insertAdmin(this.username, this.password, this.phoneNumber);
            setAdminId(username);
            am.adminMenu();
        }

    }

    public void setAdminId(String username) {
        try {
            String querry = "SELECT id FROM admin WHERE username = ?";
            PreparedStatement pst = DatabaseUtil.getConnection().prepareStatement(querry);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                this.adminId = rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getAdminCounter() {
        return adminCounter;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getName() {
        return username;
    }

    public Boolean checkPassword(String password, Admin a) {
        return password.equals(a.password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean setUserName(Admin a) {
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
        boolean b = getUserName(name);
        if (!b) {
            a.username = name;
            return true;
        }
        System.out.println("Username already exists !");
        return false;

    }

    public boolean getUserName(String username) {
        String querry = "SELECT * FROM admin WHERE username = ?";
        PreparedStatement pst;
        try {
            pst = DatabaseUtil.getConnection().prepareStatement(querry);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    public void setPhoneNumber(Admin a) {
        String ph;
        while (true) {
            System.out.print("Enter phone number: ");
            ph = scanner.nextLine();

            if (isValidPhoneNumber(phoneNumber)) {
                a.phoneNumber = ph;
                break;
            } else {
                System.out.println("Invalid phone number. Please re-enter the phone number.");
            }
        }

        System.out.println("Phone number set successfully!");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^[0-9]{10}$";
        return Pattern.matches(phonePattern, phoneNumber);
    }

    public void setPassword(Admin a) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        // Password must contain at least one digit, one lowercase,
        // one uppercase letter, and be at least 8 characters long
        while (true) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (Pattern.matches(regex, password)) {
                a.password = password;
                return;
            } else {
                System.out.println(
                        "Invalid password. Password must be at least 8 characters long, contain at least one digit, one lowercase letter, and one uppercase letter.");
                System.out.println();
            }
        }
    }
}
