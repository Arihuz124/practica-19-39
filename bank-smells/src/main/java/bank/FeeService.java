package bank;
class FeeService {
    double feeFor(Account from, Account to, double amount, String channel) {
        double fee = 0.0;
        if ("VIP".equalsIgnoreCase(from.type)) fee = amount * 0.01;
        else fee = amount * 0.02;
        if ("INTERNAL".equalsIgnoreCase(channel)) fee = fee * 0.5;
        if (amount > 1000) fee = fee + 1.50;
        else fee = fee + 0.50;
        if (from.currency.equalsIgnoreCase(to.currency)) fee = fee - 0.25;
        return Math.max(fee, 0.0);
    }
}