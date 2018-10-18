import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillGeneratorTest {

    @Test
    void testValidPriceForSingleItem(){
        BillGenerator billGenerator = new BillGenerator();
        List<Integer> basket = new ArrayList<>();
        int barcodeForBeer = 1001;
        basket.add(barcodeForBeer);
        BigDecimal expected = BigDecimal.valueOf(1.2);

        BigDecimal result = billGenerator.getPrice(basket);

        assertEquals(expected, result);
    }
}