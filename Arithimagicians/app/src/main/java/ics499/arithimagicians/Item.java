package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Item implements Serializable{
    private String name;
    private String bonus;
    private int value;

    public Item() {
        this.name = "";
        this.bonus = "";
        this.value = 0;
    }

    public Item(String name, String bonus, int value) {
        this.name = name;
        this.bonus = bonus;
        this.value = value;

    }

    public int getValue(){
        return value;
    }

    public void decrementValue(){
        this.value--;
    }
}
