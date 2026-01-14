package bank;
class Customer {
    private final String id;
    private final Profile profile;
    Customer(String id, Profile profile) {
        this.id = id;
        this.profile = profile;
    }
    String getId() { return id; }
    Profile getProfile() { return profile; }
}