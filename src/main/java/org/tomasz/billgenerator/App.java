package org.tomasz.billgenerator;

import java.math.BigDecimal;
import java.util.List;

public class App {

    public static void main(String[] args){
        BillGenerator billGenerator = new BillGenerator();
        ShopFileReader shopFileReader = new ShopFileReader();

        List<Integer> basket = shopFileReader.readBasket("basket.txt");

        BigDecimal price = billGenerator.getBill(basket);
        System.out.println(price);
    }
}
