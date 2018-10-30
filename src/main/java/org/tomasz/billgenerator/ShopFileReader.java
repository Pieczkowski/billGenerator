package org.tomasz.billgenerator;

import org.tomasz.billgenerator.model.ItemsPriceMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShopFileReader {

    private String fileName = "product_prices.csv";

    ShopFileReader(String fileName){
        this.fileName = fileName;
    }

    ShopFileReader(){}

    public ItemsPriceMap readPrices() {
        ItemsPriceMap itemsPriceMap = new ItemsPriceMap();
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine(); // skip first line

            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                itemsPriceMap.put(Integer.valueOf(splitLine[0].trim()), splitLine[1].trim(), Integer.valueOf(splitLine[2].trim()), BigDecimal.valueOf(Double.valueOf(splitLine[3].trim())));
            }
            return itemsPriceMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> readBasket(String basketFileName){
        List<Integer> basket = new ArrayList<>();
        String line;
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(basketFileName).getFile());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            while ((line = bufferedReader.readLine()) != null) {
                basket.add(Integer.valueOf(line.trim()));
            }

            return basket;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}