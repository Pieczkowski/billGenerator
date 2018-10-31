package org.tomasz.billgenerator;

import org.tomasz.billgenerator.customException.NoItemInProductPricesListException;
import org.tomasz.billgenerator.customException.NoPriceForSingleItemException;
import org.tomasz.billgenerator.model.Item;
import org.tomasz.billgenerator.model.ItemsPriceMap;

import java.math.BigDecimal;
import java.util.HashMap;
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
            if (item == null){
                throw new NoItemInProductPricesListException();
            }
            price = price.add(getPriceOfSingleGroup(item, amount));
        }
        return price;
    }


    private Map<Integer, Integer> groupBasketItems(List<Integer> basket) {
        Map<Integer, Integer> groupedBasket = new HashMap<>();

        for (int item : basket){
            groupedBasket.put(item, groupedBasket.getOrDefault(item, 0) + 1);
        }
        return groupedBasket;
    }

    private int findBiggestValidAmount(Item item, int amount){
        int biggestAmount = 0;
        Map<Integer, BigDecimal> amountAndPrices = item.getAmountAndPrices();
        for (Integer amountInMap : amountAndPrices.keySet()){
            if (amountInMap > biggestAmount && amountInMap <= amount){
                biggestAmount = amountInMap;
            }
        }
        if (biggestAmount == 0)
            throw new NoPriceForSingleItemException();

        return biggestAmount;
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

    public void removeItemPrice(int id, int amount){
        productPrices.get(id).removePrice(amount);
    }

    public ItemsPriceMap getProductPrices() {
        return productPrices;
    }
}
