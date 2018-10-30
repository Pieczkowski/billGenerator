package org.tomasz.billgenerator.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Item {
    private int id;
    private String name;
    private Map<Integer, BigDecimal> amountAndPrices = new HashMap<>();


    public Item(int id, String name, Integer amount, BigDecimal price){
        this.id = id;
        this.name = name;
        amountAndPrices.put(amount, price);
    }

    public int getId() {
        return id;
    }

    public void add(Integer amount, BigDecimal price){
        amountAndPrices.put(amount, price);
    }

    public BigDecimal getPriceForAmount(Integer amount){
        return amountAndPrices.get(amount);
    }

    public Map<Integer, BigDecimal> getAmountAndPrices() {
        return amountAndPrices;
    }

    public String getName() {
        return name;
    }

    public void removePrice(int amount){
        amountAndPrices.remove(amount);
    }
}
