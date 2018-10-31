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

    /**
     * Produces total value that customer is required to pay for items in his basket
     * @param basket list of items customer wants to buy
     * @return total price of given basket
    */
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


    /**
     * Groups items with same id
     * @param basket list of items customer wants to buy
     * @return grouped items in form of map: barcode - amount
     */
    private Map<Integer, Integer> groupBasketItems(List<Integer> basket) {
        Map<Integer, Integer> groupedBasket = new HashMap<>();

        for (int item : basket){
            groupedBasket.put(item, groupedBasket.getOrDefault(item, 0) + 1);
        }
        return groupedBasket;
    }

    /**
     * Finds biggest amount of item that has defined price
     * <p> Example
     * <p> Having the following prices for chocolate in the store:
     * <p> 1 - 3.15
     * <p> 2 - 6
     * <p> 4 - 11
     *
     * <p> If we have <b>3</b> in the basket we expect the result to be <b>2</b>.
     * <p> If we add one more beer the result should be <b>4</b>.
     */
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

    /**
     * Calculate price for a single grouped item using the biggest available pricing groups
     * @param item item to be evaluated
     * @param amount amount of given item in basket
     */
    private BigDecimal getPriceOfSingleGroup(Item item, int amount) {
        int biggestSmallerAmountInMap = findBiggestValidAmount(item, amount);
        BigDecimal price = BigDecimal.ZERO;
        if (amount - biggestSmallerAmountInMap > 0) {
            price = price.add(getPriceOfSingleGroup(item, amount - biggestSmallerAmountInMap));
        }
        price = price.add(item.getPriceForAmount(biggestSmallerAmountInMap));
        return price;
    }

    /**
     * Remove price of a given item for specified amount from the data
     * @param id product id (barcode)
     * @param amount of items that will no longer have specified price
     */
    public void removeItemPrice(int id, int amount){
        productPrices.get(id).removePrice(amount);
    }

    public ItemsPriceMap getProductPrices() {
        return productPrices;
    }
}
