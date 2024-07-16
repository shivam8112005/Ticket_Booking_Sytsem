class Customer extends User {
    private String username;
    private String password;

    private static int customerIdCounter = 1; // Static counter for unique IDs

    public Customer(String name, String phoneNumber, Ticket ticket, String dateOfBirth, DiscountPass discountPass, String username, String password) {
        super(customerIdCounter++, name, phoneNumber, ticket, dateOfBirth, discountPass);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
