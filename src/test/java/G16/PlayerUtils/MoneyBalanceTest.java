package G16.PlayerUtils;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class MoneyBalanceTest extends TestCase {

    @Test
    public void testUpdateMoney() {
        MoneyBalance balance = new MoneyBalance();
        balance.setStartingBalance();
        balance.updateMoney(1000);
        assertEquals(31000, balance.getBalance());
        balance.updateMoney(-1000);
        assertEquals(30000, balance.getBalance());
    }

}