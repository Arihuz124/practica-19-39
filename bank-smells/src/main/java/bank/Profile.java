package bank;
class Profile {
    private final Address address;
    Profile(Address address) {
        this.address = address;
    }
    Address getAddress() { return address; }
}