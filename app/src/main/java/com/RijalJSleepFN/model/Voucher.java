package com.RijalJSleepFN.model;



/**

 This class represents a voucher that can be applied to a price.
 @author Ibrahim Rijal
 */
public class Voucher extends Serializable {

    public Type type;
    public double cut;
    public String name;
    public int code;
    public double minimum;
    private boolean used;



    /**
     Constructs a new Voucher object with the specified parameters.
     @param name The name of the voucher
     @param code The code of the voucher
     @param type The type of voucher
     @param minimum The minimum amount required for the voucher to be applied
     @param cut The amount of discount or cut for the voucher
     @param used Whether the voucher has been used or not
     */

    public Voucher(String name, int code, Type type, double minimum, double cut, boolean used) {

        this.name = name;
        this.code = code;
        this.type = type;
        this.minimum = minimum;
        this.cut = cut;
        this.used = used;
    }
    /**
     Determines whether the voucher can be applied to the specified price.
     @param price The price to check if the voucher can be applied to
     @return true if the voucher can be applied to the specified price, false otherwise
     */
    public boolean canApply(Price price){
        if (price.price > this.minimum && this.used == false){
            return true;
        }
        return false;
    }


    /**
     Applies the voucher to the specified price.
     @param price The price to apply the voucher to
     @return The new price after applying the voucher
     */
    public double apply(Price price){
        
        this.used = true;
        if(this.type ==  Type.DISCOUNT){
            if(this.cut > 100){
                this.cut = 100;
            }
            return price.price - (price.price * this.cut / 100);
        }
        if(this.cut > price.price){
            this.cut = price.price;
        }
        return price.price - this.cut;
        
        
    }
    /**
     * Returns a boolean indicating whether the voucher has been used or not.
     *
     * @return true if the voucher has been used, false otherwise
     */
    public boolean isUsed(){
        return this.used;
    }

   // @Override

} 
    

