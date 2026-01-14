package bank;
class Transaction {
    final String fromId;
    final String toId;
    final double amount;
    final String currency;
    final String reference;
    Transaction(String fromId, String toId, double amount, String currency, String reference) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
        this.currency = currency;
        this.reference = reference;
    }
}