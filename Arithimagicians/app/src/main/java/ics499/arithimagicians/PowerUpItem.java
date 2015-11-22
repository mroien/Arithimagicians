package ics499.arithimagicians;

/**
 * Created by Wiggles on 10/15/2015.
 */
public class PowerUpItem extends Item {

    /**
     * Standarad Constructor
     * @param name
     * @param bonus
     * @param quantity
     */
    public PowerUpItem(String name, String bonus, int quantity) {
        super(name, bonus, quantity);
    }

    /**
     * Constructor using Type
     * @param type
     * @param bonus
     * @param quantity
     */
    public PowerUpItem(Type type, String bonus, int quantity) {
        super(type.getName(), bonus, quantity);


    }

}
