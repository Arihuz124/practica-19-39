package bank;
class Account {
    String id;
    String type;
    String currency;
    double balance;
    Account(String id, String type, String currency, double balance) {
        this.id = id;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
    }
}