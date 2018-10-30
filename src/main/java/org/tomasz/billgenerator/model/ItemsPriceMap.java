package org.tomasz.billgenerator.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class ItemsPriceMap {

    private List<Item>[] elements;
    private int size;
    private static final int DEFAULT_SIZE = 10;

    public ItemsPriceMap(int size){
        this.size = size;
        elements = new LinkedList[size];

        for (int i = 0; i < size; i++) {
            elements[i] = new LinkedList<>();
        }
    }

    public ItemsPriceMap(){
        this(DEFAULT_SIZE);
    }

    public int size() {
        return size;
    }

    public boolean containsID(Integer id) {
        int index = id.hashCode() % size;

        for (Item item : elements[index]){
            if (item.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Item get(Integer id) {
        int index = id.hashCode() % size;

        for (Item item : elements[index]){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public void put(Integer id, String name, int amount, BigDecimal price) {
        int index = id.hashCode() % size;

        for (Item item : elements[index]){
            if (item.getId() == id){
                item.add(amount, price);
                return;
            }
        }
        elements[index].add(new Item(id, name, amount, price));
    }

    public boolean removeItem(Integer id) {
        int index = id.hashCode() % size;
        for (Item item : elements[index]){
            if (item.getId() == id){
                elements[index].remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (Item item : elements[i]){
                sb.append(String.format("%s: %.2f\n", item.getName(), item.getPriceForAmount(1)));
            }
        }
        return sb.toString();
    }
}
