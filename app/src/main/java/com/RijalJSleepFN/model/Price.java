package com.RijalJSleepFN.model;

/**
 * A class representing a price for a product, with optional discount and rebate.
 *
 * @author Ibrahim Rijal
 */
public class Price {
    
    public double price;
    public int discount;


    /**
     * Creates a new price with the given price and no discount or rebate.
     *
     * @param price The price of the product.
     */
    public Price (double price){
        this.price = price;
        this.discount = 0;
       // this.rebate = 0;
    }

    /**
     * Creates a new price with the given price and discount, but no rebate.
     *
     * @param price The price of the product.
     * @param discount The discount of the product, in percentage.
     */
    public Price(double price, int discount){
        this.price = price;
        this.discount = discount;
        //this.rebate = 0;
    }


    /**
     * Returns a string representation of the price, including the discount and rebate (if any).
     *
     * @return A string representation of the price.
     */
    public String toString() {
        return "discount=" + this.discount + ", price=" + this.price;
    }

   /*  public Price(double price, double rebate){
        this.price = price;
        //this.rebate = discount;
        this.discount = 0;
        
   } */

   /* 
    private double getDiscountedPrice(){
        if(this.discount >= 100){
            this.discount = 100;
            return 0;
        }else{
            return price - (price * discount / 100);
        }
        
    }

    private double getRebatedPrice(){
        if(this.rebate > this.price){
            this.rebate = this.price;
        }
            return this.price - this.rebate;

    }
    */
    
}
