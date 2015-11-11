package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Item implements Serializable{


    public static enum Type {
        HEALTHPOTION ("HP\nPotion"),
        XPBONUS ("XP\nBonus"),
        DMGBONUS ("Dmg\nBonus"),
        LOOTBONUS ("Loot\nBonus"),
        HPFULLREFRESH ("HP\nRefresh"),
        HPREGEN ("Regen\nPotion"),
        LAST ("Last Item");

        private final int index;
        private final String name;
        Type(String name){
            this.name = name;
            index = Item.Type.this.ordinal();
        }

        public String getName(){
            return name;
        }
        public int getIndex() { return index; }
    }

    public static final int HEALTHPOTION = Type.HEALTHPOTION.getIndex();
    public static final int XPBONUS = Type.XPBONUS.getIndex();
    public static final int DMGBONUS = Type.DMGBONUS.getIndex();
    public static final int LOOTBONUS = Type.LOOTBONUS.getIndex();
    public static final int HPFULLREFRESH = Type.HPFULLREFRESH.getIndex();
    public static final int HPREGEN = Type.HPREGEN.getIndex();

    private String name;
    private String bonus;
    private int quantity;

    public Item() {
        this.name = "";
        this.bonus = "";
        this.quantity = 0;
    }

    public Item(String name, String bonus, int value) {
        this.name = name;
        this.bonus = bonus;
        this.quantity = value;

    }

    public int getQuantity(){
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getBonus(){
        return bonus;
    }

    public void decrementValue(){
        this.quantity--;
    }
}
