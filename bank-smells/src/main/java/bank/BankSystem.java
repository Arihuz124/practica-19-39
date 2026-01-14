package bank;
import java.util.*;
public class BankSystem {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Customer> customers = new HashMap<>();
    private final List<Transaction> history = new ArrayList<>();
    private final FeeService feeService = new FeeService();
    private final AuditLogger audit = new AuditLogger();

    public void addAccount(Account account) { accounts.put(account.id, account); }
    public void addCustomer(Customer customer) { customers.put(customer.getId(), customer); }
    public double getBalance(String accountId) {
        Account a = accounts.get(accountId);
        if (a == null) throw new IllegalArgumentException("Account not found");
        return a.balance;
    }

    public String transfer(String fromId,String toId,double amount,String currency,String channel,
        String reference,String note,String performedByCustomerId,boolean applyFee,boolean allowOverdraft) {

        if (fromId == null || toId == null) return "ERROR";
        if (fromId.equalsIgnoreCase(toId)) return "ERROR_SAME_ACCOUNT";
        if (amount <= 0) return "ERROR_AMOUNT";
        if (currency == null || currency.isBlank()) return "ERROR_CURRENCY";

        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null) return "ERROR_NOT_FOUND";
        if (!from.currency.equalsIgnoreCase(currency) || !to.currency.equalsIgnoreCase(currency))
            return "ERROR_CURRENCY_MISMATCH";

        Customer performer = customers.get(performedByCustomerId);
        String city = (performer == null) ? "UNKNOWN" :
            performer.getProfile().getAddress().getCity();

        if ("Guayaquil".equalsIgnoreCase(city) && amount > 3000) return "ERROR_CITY_LIMIT";
        if ("Quito".equalsIgnoreCase(city) && amount > 2000) return "ERROR_CITY_LIMIT";

        if ("ATM".equalsIgnoreCase(channel) && amount > 500) return "ERROR_CHANNEL_LIMIT";
        if ("ATM".equalsIgnoreCase(channel) && note != null && note.length() > 120)
            return "ERROR_NOTE";

        double fee = applyFee ? feeService.feeFor(from, to, amount, channel) : 0.0;
        double totalDebit = amount + fee;

        if (!allowOverdraft && from.balance < totalDebit) return "ERROR_FUNDS";
        if ("SAVINGS".equalsIgnoreCase(from.type) && from.balance < totalDebit)
            return "ERROR_FUNDS";

        from.balance = from.balance - totalDebit;
        to.balance = to.balance + amount;

        history.add(new Transaction(fromId, toId, amount, currency, reference));
        audit.log("Transfer ok ref=" + reference + " note=" + note + " by=" + performedByCustomerId);
        return "OK";
    }

    String generateMonthlyStatement(String accountId) {
        return "STATEMENT:" + accountId + ":" + history.size();
    }
}