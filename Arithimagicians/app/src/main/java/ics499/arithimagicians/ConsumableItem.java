package ics499.arithimagicians;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 * Consumable item class
 */
public class ConsumableItem extends Item {

    /**
     * Public constructor to create the consumable item by sending the values up to the super Item class
     */
    public ConsumableItem(String name, String bonus, int value) {
        super(name, bonus, value);
    }
}
