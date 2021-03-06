package org.tomasz.billgenerator;

import org.junit.jupiter.api.Test;
import org.tomasz.billgenerator.customException.NoItemInProductPricesListException;
import org.tomasz.billgenerator.customException.NoPriceForSingleItemException;

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

        BigDecimal result = billGenerator.getBill(basket);

        assertEquals(expected, result);
    }

    @Test
    void testValidPriceForMultipleItems(){
        BillGenerator billGenerator = new BillGenerator();
        List<Integer> basket = new ArrayList<>();
        int barcodeForBeer = 1001;
        int barcodeChocolate = 3401;
        basket.add(barcodeForBeer);
        basket.add(barcodeForBeer);
        basket.add(barcodeForBeer);
        basket.add(barcodeChocolate);
        BigDecimal expected = BigDecimal.valueOf(6.35);

        BigDecimal result = billGenerator.getBill(basket);

        assertEquals(expected, result);
    }

    @Test
    void testBillIs0WhenBasketEmpty(){
        BillGenerator billGenerator = new BillGenerator();
        List<Integer> basket = new ArrayList<>();
        BigDecimal expected = BigDecimal.ZERO;

        assertEquals(expected, billGenerator.getBill(basket));
    }

    @Test
    void testNoPriceForSingleItemExceptionThrown(){
        BillGenerator billGenerator = new BillGenerator();
        List<Integer> basket = new ArrayList<>();
        int barcodeForBeer = 1001;
        basket.add(barcodeForBeer);
        billGenerator.removeItemPrice(barcodeForBeer, 1);

        assertThrows(NoPriceForSingleItemException.class, () -> billGenerator.getBill(basket));
    }

    @Test
    void testNoPriceForSingleItemExcetionThrown(){
        BillGenerator billGenerator = new BillGenerator();
        List<Integer> basket = new ArrayList<>();
        int invalidBarcode = 1002;
        basket.add(invalidBarcode);

        assertThrows(NoItemInProductPricesListException.class, () -> billGenerator.getBill(basket));
    }

}