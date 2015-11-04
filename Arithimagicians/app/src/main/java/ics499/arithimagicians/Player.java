package ics499.arithimagicians;

import android.content.ClipData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Player extends Character implements Serializable {
    private ArrayList<Die> dice;
    private ArrayList<Die> diceUsed;
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
        this.diceUsed = new ArrayList<Die>();
        Die dice = new Die();
        Die dice6 = new Die(6, "d6", 0);
        Die dice8 = new Die(8, "d8", 0);
        this.dice.add(dice);
        this.dice.add(dice6);
        this.dice.add(dice6);
        this.dice.add(dice8);
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

    public ArrayList<Die> getDice(){ return this.dice;}

    public void setDiceUsed(Die dice){
        diceUsed.add(dice);
    }

    public ArrayList<Die> getDiceUsed(){
        return diceUsed;
    }

    public void resetDiceUsed(){
        diceUsed = new ArrayList<Die>();
    }

    public void swapDiceBackToInv(){
        for(Die d : diceUsed){
            Die temp = d;
            dice.add(d);
        }
        diceUsed.clear();
    }

    public void setLevel(String level){
        this.lastStage = level;
    }
}
