package org.tomasz.billgenerator.customException;

public class NoPriceForSingleItemException extends RuntimeException {
    public NoPriceForSingleItemException(String exceptionMessage){
        super(exceptionMessage);
    }

    public NoPriceForSingleItemException(){
        this("There is no single item in the pricing list.");
    }
}
