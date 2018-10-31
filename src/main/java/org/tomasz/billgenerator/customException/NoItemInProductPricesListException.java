package org.tomasz.billgenerator.customException;

public class NoItemInProductPricesListException extends RuntimeException {
    public NoItemInProductPricesListException(String exceptionMessage){
        super(exceptionMessage);
    }

    public NoItemInProductPricesListException(){
        this("No item in list.");
    }

}
