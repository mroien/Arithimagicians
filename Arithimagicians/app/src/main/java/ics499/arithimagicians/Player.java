package ics499.arithimagicians;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */


public class Player extends Character implements Serializable {

    private ArrayList<Die> dice;
    private ArrayList<Die> diceUsed;
    private int xp;
    private ArrayList<Item> inventory;
    private Double lootRate;
    private Double xPRate;
    private Double damageRate;
    private Double regenRate;
    private String lastStage;
    private int userId;
    private double totalRolls;
    private double totalHits;
    private double highestAcc;
    private int maxTotalDamage;
    private long xpTimeStamp;
    private long dmgBonusTimeStamp;
    private long lootTimeStamp;
    private long hpRefreshTimeStamp;
    private long hpRegenTimeStamp;


    private long timeStamp;

    private int maxSingleDamage;

    public Player() {
        super(10, 1);
        this.xp = 0;
        this.dice = new ArrayList<Die>();
        this.diceUsed = new ArrayList<Die>();
        Die dice = new D4();
        Die dice6 = new D6();
        Die dice6Two = new D6();
        Die dice8 = new D8();
        this.dice.add(dice);
        this.dice.add(dice6);
        this.dice.add(dice6Two);
        this.dice.add(dice8);
        this.xp = 0;
        this.inventory = new ArrayList<Item>(6);
        this.lootRate = 1.0;
        this.xPRate = 1.0;
        this.damageRate = 1.0;
        this.regenRate = 1.0;
        this.lastStage = "1_5";
        this.userId = 0;
        this.highestAcc = 100;
        this.timeStamp = System.nanoTime();
        prepareInventory();
    }

    /**
     * Populates the inventory array with null values so items can be added out of order.
     * Gives the player one HP potion to begin the game with.
     */
    private void prepareInventory() {
        //set up inventory as an empty list
        for (int i = 0; i < 6; i++) {
            inventory.add(i, null);
        }
        ConsumableItem healthPotion = new ConsumableItem(Item.Type.HEALTHPOTION.getName(), "5", 1);
        inventory.add(Item.Type.HEALTHPOTION.ordinal(), healthPotion);
    }

    /**
     * Takes an item and adds it to the inventory list according to the Item.Type value for that item.
     * If the item is not listed in the Item.Type enum, it's not a valid item and isn't added.
     * @param item
     */
    public void addItem(Item item) {
        int index = -1;
        for (Item.Type type : Item.Type.values()) {
            if (type.getName().equals(item.getName())) {
                if (inventory.get(type.ordinal()) == null) {
                    inventory.add(type.ordinal(), new PowerUpItem(type.getName(), type.getBonus(), 0));
                }
                inventory.get(type.ordinal()).incrementValue();
            }
        }

        // index is > -1 iff item is a valid item in the Item.Type enum
        if (index > -1) {
            inventory.add(index, item);
        }
    }

    /**
     * Verifies that the player actually has an item of the correct type. If so, this method applies
     * the effects to the Player object by the item enum type.
     * @param item
     */
    public void useItem(Item item) {
        if (item.getQuantity() > 0) {
            String name = item.getName();
            switch (name) {
                case ("HP\nPotion"):
                    this.gainHealth(Integer.parseInt(item.getBonus()));
                    item.decrementValue();
                    break;
                case ("XP\nBonus"):
                    xPRate = xPRate * Double.parseDouble(item.getBonus());
                    xpTimeStamp = System.nanoTime();
                    item.decrementValue();
                    break;
                case ("Dmg\nBonus"):
                    damageRate = damageRate * Double.parseDouble(item.getBonus());
                    dmgBonusTimeStamp = System.nanoTime();
                    item.decrementValue();
                    break;
                case ("Loot\nBonus"):
                    lootRate = lootRate * Double.parseDouble(item.getBonus());
                    lootTimeStamp = System.nanoTime();
                    item.decrementValue();
                    break;
                case ("HP\nRefresh"):
                    int hpDiff = getTotalHealth() - getCurrentHealth();
                    hpRefreshTimeStamp = System.nanoTime();
                    item.decrementValue();
                    gainHealth(hpDiff);
                    break;
                case ("Regen\nPotion"):
                    regenRate = regenRate * Double.parseDouble(item.getBonus());
                    hpRegenTimeStamp = System.nanoTime();
                    item.decrementValue();
                    break;
            }
        }
    }

    /**
     * Increases the Player's experience points by the passed amount.
     * @param amt
     */
    public void gainXP(int amt) {
        xp += amt;
    }

    /**
     * Decreases the Player's experience points by the passed amount.
     * @param amt
     */
    public void spendXP(int amt) {
        xp -= amt;
    }

    /**
     * Returns a string indicating the last level the Player has completed.
     * @return
     */
    public String getLastMap() {
        return this.lastStage;
    }

    /**
     * Returns the list containing the Player's inventory.
     * @return
     */
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    /**
     * Sets the Player's inventory to the passed ArrayList of Items. Used by Activities
     * that affect the player's inventory.
     * @param inv
     */
    public void setInventory(ArrayList<Item> inv) {
        this.inventory = inv;
    }

    /**
     * Returns the Player's Die ArrayList
     * @return
     */
    public ArrayList<Die> getDice() {
        return this.dice;
    }

    /**
     * Moves a Die from the Player's main ArrayList into the diceUsed ArrayList
     * @param dice
     */
    public void setDiceUsed(Die dice) {
        diceUsed.add(dice);
    }

    /**
     * Returns the ArrayList of the Player's dice.
     * @return
     */
    public ArrayList<Die> getDiceUsed() {
        return diceUsed;
    }

    public void resetDiceUsed() {
        diceUsed = new ArrayList<Die>();
    }

    /**
     * Moves used dice back to the Player's main dice list.
     */
    public void swapDiceBackToInv() {
        for (Die d : diceUsed) {
            Die temp = d;
            dice.add(d);
        }
        diceUsed.clear();
    }

    /**
     * Sets the Player's level to the passed level string.
     * @param level
     */
    public void setLevel(String level) {
        this.lastStage = level;
    }

    /**
     * Checks to see if the Player has a dice of the given dice type.
     * @param dice
     * @return
     */
    public boolean checkDice(String dice) {
        for (Die d : this.dice) {
            if (d.getDiceType().equals(dice)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Player's XP total.
     * @return
     */
    public int getXp() {
        return xp;
    }

    /**
     * Returns the Player's rate of finding loot.
     * @return
     */
    public double getLootRate() {
        return lootRate;
    }

    /**
     * Returns the Player's damage rate. A rate > 1.0 indicates the player has a bonus to damage.
     * @return
     */
    public double getDamageRate() {
        return damageRate;
    }

    /**
     * Sets the user ID.
     * @param id
     */
    public void setUserId(int id) {
        this.userId = id;
    }

    /**
     * Returns the Player's user ID.
     * @return
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Returns a true if the passed value is less than the current experience total.
     * Returns false if the value is greater than the current experience total.
     * @param value
     * @return
     */
    public boolean checkXP(int value) {
        return this.getXp() >= value;
    }

    /**
     * Adds the passed Die to the Player's Die list.
     * @param die
     */
    public void addDie(Die die) {
        this.dice.add(die);
    }

    /**
     * Updates the Player's highest accuracy to the passed double.
     * @param acc
     */
    public void updateHighestAcc(double acc) {
        if (acc < this.highestAcc) {
            this.highestAcc = acc;
        }
    }

    /**
     * Increases the Player's total hits.
     */
    public void updateTotalHits() {
        this.totalHits++;
    }

    /**
     * Increases the Player's total rolls.
     */
    public void updateTotalRolls() {
        this.totalRolls++;
    }

    /**
     * Updates the Player's Maximum Damage Dealt to damage.
     * @param damage
     */
    public void updateMaxTotalDamage(int damage) {
        if (damage > this.maxTotalDamage) {
            this.maxTotalDamage = damage;
        }
    }

    /**
     * Updates Maximum Single Damage to the dmg.
     * @param dmg
     */
    public void updateMaxSingleDamage(int dmg) {
        if (dmg > this.maxSingleDamage) {
            this.maxSingleDamage = dmg;
        }
    }

    /**
     * Returns the Player's Highest Accuracy.
     * @return
     */
    public double getHighestAcc() {
        return highestAcc;
    }

    /**
     * Returns the Player's Maximum Total Damage.
     * @return
     */
    public int getMaxTotalDamage() {
        return maxTotalDamage;
    }

    /**
     * Returns the Player's Maximum Single Attack Damage.
     * @return
     */
    public int getMaxSingleDamage() {
        return maxSingleDamage;
    }

    /**
     * Returns the Player's Total Accuracy.
     * @return
     */
    public double getTotalAcc() {
        return totalHits / totalRolls * 100;
    }

    /**
     * Increases the Player's health when appropriate.
     * @param time
     */
    public void checkHealthRegen(long time) {
        Log.i("health", "" + this.getCurrentHealth());
        if (((time - this.timeStamp) / 1E9) > 300) {
            int amt = 1 * regenRate.intValue();
            this.gainHealth(amt);
            this.timeStamp = time;
        }
    }

    /**
     * Checks whether any power-ups have expired.
     * @param time
     */
    public void checkPowerupTimer(long time) {
        if (((time - this.xpTimeStamp) / 1E9) > 86400) {
            xPRate = 1.0;
        }
        if (((time - this.dmgBonusTimeStamp) / 1E9) > 86400) {
            dmgBonusTimeStamp = 1;
        }
        if (((time - this.lootTimeStamp) / 1E9) > 86400) {
            lootTimeStamp = 1;
        }
        if (((time - this.hpRefreshTimeStamp) / 1E9) > 86400) {
            hpRefreshTimeStamp = 1;
        }
        if (((time - this.hpRegenTimeStamp) / 1E9) > 86400) {
            hpRegenTimeStamp = 1;
        }
    }

}

