class Passenger extends User {
    private static int idCounter = 1;

    public Passenger(String name, String phoneNumber, Ticket ticket, String dateOfBirth, DiscountPass discountPass) {
        super(idCounter++, name, phoneNumber, ticket, dateOfBirth, discountPass);
    }

    public Passenger(Customer customer) {
        super(idCounter++, customer.getName(), customer.getPhoneNumber(), null, customer.getDateOfBirth(), null);
        // Copy other relevant data if necessary
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                '}';
    }
}
