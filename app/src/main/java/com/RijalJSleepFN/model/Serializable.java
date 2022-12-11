package com.RijalJSleepFN.model;

import java.util.HashMap;



/**

 This class represents a serializable object with an ID.
 @author Ibrahim Rijal
 */
public class Serializable implements Comparable<Serializable> {
    public final int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<Class<?>, Integer>();

    /**
     Constructs a new Serializable object with a unique ID.
     */
    protected Serializable() {
        Integer counter = mapCounter.get(getClass());
        if (counter == null){
            counter =  0;
        }
        else{
            counter +=1;
        }
        mapCounter.put(getClass(), counter);
        this.id = counter;
    }

    /**
     Sets the closing ID for the specified class.
     @param clazz The class to set the closing ID for
     @param id The closing ID to set
     @return The previous value of the closing ID
     */
    public static <T extends Serializable> Integer setClosingId(Class<T> clazz, int id) { return mapCounter.put(clazz, id); }

    /**
     Gets the closing ID for the specified class.
     @param clazz The class to get the closing ID for
     @return The closing ID for the specified class
     */
    public static <T extends Serializable> Integer getClosingId(Class<T> clazz) { return mapCounter.get(clazz); }

    /**
     Determines whether the specified object is equal to this Serializable object.
     @param other The object to compare to this Serializable object
     @return true if the specified object is equal to this Serializable object, false otherwise
     */
    public boolean equals(Object other)
    {
        return other instanceof Serializable && ((Serializable) other).id == id;
    }


    /**
     Determines whether the specified Serializable object is equal to this Serializable object.
     @param other The Serializable object to compare to this Serializable object
     @return true if the specified Serializable object is equal to this Serializable object, false otherwise
     */
    public boolean equals(Serializable other)
    {
        return other.id == id;
    }


    /**
     Compares this Serializable object to the specified Serializable object.
     @param other The Serializable object to compare to this Serializable object
     @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Serializable other)
    {
        return Integer.compare(this.id, other.id);
    }
}
