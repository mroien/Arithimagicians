package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Item implements Serializable {

    /**
     * Type stores the basic information for the items in the game and allows us to update
     * the stats in a single place without having 6 separate item subclasses with the same code.
     */
    public static enum Type {
        HEALTHPOTION("HP\nPotion"),
        XPBONUS("XP\nBonus", "XPBONUS", "1.5"),
        DMGBONUS("Dmg\nBonus", "DMGBONUS", "1.5"),
        LOOTBONUS("Loot\nBonus", "LOOTBONUS", "1.5"),
        HPFULLREFRESH("HP\nRefresh", "HPFULLREFRESH", "0"),
        HPREGEN("Regen\nPotion", "HPREGEN", "1.5"),
        LAST("Last Item");

        private final int index;
        private final String name;
        private final String nameString;
        private final String bonus;

        Type(String name) {
            this.name = name;
            this.bonus = "0";
            this.nameString = name;
            index = Item.Type.this.ordinal();
        }

        Type(String name, String nameString, String bonus) {
            this.name = name;
            this.nameString = nameString;
            this.bonus = bonus;
            index = Item.Type.this.ordinal();
        }

        public String getName() {
            return name;
        }

        public String getNameString() {
            return nameString;
        }

        public String getBonus() {
            return bonus;
        }

        public int getIndex() {
            return index;
        }
    }

    private String name;
    private String bonus;
    private int quantity;
    private long timeStamp;

    /**
     * Blank constructor.
     */
    public Item() {
        this.name = "";
        this.bonus = "";
        this.quantity = 0;
    }

    /**
     * Full Constructor.
     * @param name
     * @param bonus
     * @param value
     */
    public Item(String name, String bonus, int value) {
        this.name = name;
        this.bonus = bonus;
        this.quantity = value;

    }

    /**
     * Returns the quantity the player has of this item.
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the item's name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the bonus value of the item.
     * @return
     */
    public String getBonus() {
        return bonus;
    }

    /**
     * Decreases the quantity of the item by one.
     */
    public void decrementValue() {
        this.quantity--;
    }

    /**
     * Increase the quantity of the item by one.
     */
    public void incrementValue() {
        this.quantity++;
    }

    /**
     * Gets the enum Type of the item based on the string type.
     * @param type
     * @return
     */
    public static Type findType(String type) {
        for (Type t : Type.values()) {
            if (t.getNameString().equals(type)) {
                return t;
            }
        }
        return null;
    }

}
