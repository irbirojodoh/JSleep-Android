package com.RijalJSleepFN.model;
public class Price {
    
    public double price;
   // public double rebate;
    public int discount;
    /** */
    public Price (double price){
        this.price = price;
        this.discount = 0;
       // this.rebate = 0;
    }

    public Price(double price, int discount){
        this.price = price;
        this.discount = discount;
        //this.rebate = 0;
    }


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
