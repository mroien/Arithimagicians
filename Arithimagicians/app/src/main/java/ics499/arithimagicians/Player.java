package ics499.arithimagicians;

import android.content.ClipData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Player extends Character implements Serializable {
    private ArrayList<Die> dice;
    private int xp;
    private ArrayList<PowerUpItem> powerUps;
    private ArrayList<Item> inventory;
    private Double lootRate;
    private Double xPRate;
    private Double damageRate;
    private Double regenRate;
    private String lastStage;

    public Player() {
        // Change to actual values
        super(100, 1);
        this.dice = new ArrayList<Die>();
        this.xp = 0;
        this.powerUps = new ArrayList<PowerUpItem>();
        this.inventory = new ArrayList<Item>();
        this.lootRate = 1.0;
        this.xPRate = 1.0;
        this.damageRate = 1.0;
        this.regenRate = 1.0;
        this.lastStage = "1_1";
        prepareInventory();
    }

    private void prepareInventory() {
        ConsumableItem healthPotion = new ConsumableItem("Health Potion", "+5", 2);
        inventory.add(healthPotion);
    }

    private void loseHealth(int damage) {

    }

    private void addItem(Item item) {

    }

    private void useItem(Item item) {

    }

    private void gainHealth(int amt) {

    }

    private void gainXP(int amt) {

    }

    private void spendXP(int amt) {

    }
    public String getLastMap(){
        return this.lastStage;
    }

    public ArrayList<Item> getInventory(){
        return this.inventory;
    }

    public void setInventory(ArrayList<Item> inv){
        this.inventory = inv;
    }

}
