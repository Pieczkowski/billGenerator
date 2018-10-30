package org.tomasz.billgenerator;

import org.tomasz.billgenerator.model.Item;
import org.tomasz.billgenerator.model.ItemsPriceMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BillGenerator {

    private ItemsPriceMap productPrices;

    BillGenerator(String fileName) {
        ShopFileReader shopFileReader = new ShopFileReader(fileName);
        productPrices = shopFileReader.readPrices();
    }

    BillGenerator(){
        ShopFileReader shopFileReader = new ShopFileReader();
        productPrices = shopFileReader.readPrices();
    }

    public BigDecimal getBill(List<Integer> basket) {
        Map<Integer, Integer> groupedBasket = groupBasketItems(basket);
        BigDecimal price = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : groupedBasket.entrySet()){
            Integer itemID = entry.getKey();
            Integer amount = entry.getValue();
            Item item = productPrices.get(itemID);
            price = price.add(getPriceOfSingleGroup(item, amount));
        }
        return price;
    }


    private Map<Integer, Integer> groupBasketItems(List<Integer> basket) {
        //TODO
    }

    private int findBiggestValidAmount(Item item, int amount){
        //TODO
    }

    private BigDecimal getPriceOfSingleGroup(Item item, int amount) {
        int biggestSmallerAmountInMap = findBiggestValidAmount(item, amount);
        BigDecimal price = BigDecimal.ZERO;
        if (amount - biggestSmallerAmountInMap > 0) {
            price = price.add(getPriceOfSingleGroup(item, amount - biggestSmallerAmountInMap));
        }
        price = price.add(item.getPriceForAmount(biggestSmallerAmountInMap));
        return price;
    }
}
