package bank;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BankSystemTest {
    private BankSystem bank;

    @BeforeEach
    void setup() {
        bank = new BankSystem();
        bank.addAccount(new Account("A1","VIP","USD",5000));
        bank.addAccount(new Account("A2","SAVINGS","USD",100));
        bank.addAccount(new Account("A3","REGULAR","USD",0));
        bank.addCustomer(new Customer("C1", new Profile(new Address("Guayaquil"))));
        bank.addCustomer(new Customer("C2", new Profile(new Address("Quito"))));
    }

    @Test
    void transfer_ok() {
        String r = bank.transfer("A1","A3",1000,"USD","INTERNAL","R1","n","C1",true,false);
        assertEquals("OK", r);
    }

    @Test
    void city_limit_guayaquil() {
        assertEquals("ERROR_CITY_LIMIT",
            bank.transfer("A1","A3",3500,"USD","APP","R2","n","C1",false,false));
    }

    @Test
    void insufficient_funds() {
        assertEquals("ERROR_FUNDS",
            bank.transfer("A2","A3",120,"USD","APP","R3","n","C2",false,false));
    }
}