package gr.aueb.team1.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockHoldingTest {

    @Test
    void GettersSetters() {
        StockHolding sh = new StockHolding();
        sh.setStock(null);
        sh.setAmount(10);
        sh.setUser(null);
        sh.setCommittedAmount(10);
        sh.setAuthStock(null);

        Assertions.assertTrue(sh.getAmount() == 10 && sh.getCommittedAmount() == 10);

        Assertions.assertNull(sh.getStock());
        Assertions.assertNull(sh.getAuthStock());
        Assertions.assertNull(sh.getUser());
    }
}
