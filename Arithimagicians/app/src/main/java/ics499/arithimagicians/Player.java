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
        // Change to actual values
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
        this.lastStage = "1_3";
        this.userId = 0;
        this.highestAcc = 100;
        this.timeStamp = System.nanoTime();
        prepareInventory();
    }

    private void prepareInventory() {
        //set up inventory as an empty list
        for (int i = 0; i < 6; i++) {
            inventory.add(i, null);
        }
        ConsumableItem healthPotion = new ConsumableItem(Item.Type.HEALTHPOTION.getName(), "5", 2);
        inventory.add(Item.Type.HEALTHPOTION.ordinal(), healthPotion);
        inventory.add(Item.Type.DMGBONUS.ordinal(), new PowerUpItem(Item.Type.DMGBONUS.getName(), "1.5", 1));
        inventory.add(Item.Type.LOOTBONUS.ordinal(), new PowerUpItem(Item.Type.LOOTBONUS.getName(), "1.5", 1));
    }

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
        if (index > -1) {
            inventory.add(index, item);
        }
    }

    public void useItem(Item item) {
        if (item.getQuantity() > 0) {
            String name = item.getName();
            switch (name) {
                case ("HP\nPotion"):
                    int missingHealth = getTotalHealth() - getCurrentHealth();
                    if (missingHealth > Integer.parseInt(item.getBonus())) {
                        gainHealth(Integer.parseInt(item.getBonus()));
                    } else {
                        gainHealth(missingHealth);
                    }

                    item.decrementValue();
                    Log.i("potion", "Player life is now " + getCurrentHealth());
                    break;
                case ("XP\nBonus"):
                    //set clock. need a timer to reset to bonus to 1 on all bonus items
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

    public void gainXP(int amt) {
        xp += amt;
    }

    public void spendXP(int amt) {
        xp -= amt;
    }

    public String getLastMap() {
        return this.lastStage;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public void setInventory(ArrayList<Item> inv) {
        this.inventory = inv;
    }

    public ArrayList<Die> getDice() {
        return this.dice;
    }

    public void setDiceUsed(Die dice) {
        diceUsed.add(dice);
    }

    public ArrayList<Die> getDiceUsed() {
        return diceUsed;
    }

    public void resetDiceUsed() {
        diceUsed = new ArrayList<Die>();
    }

    public void swapDiceBackToInv() {
        for (Die d : diceUsed) {
            Die temp = d;
            dice.add(d);
        }
        diceUsed.clear();
    }

    public void setLevel(String level) {
        this.lastStage = level;
    }

    public boolean checkDice(String dice) {
        for (Die d : this.dice) {
            if (d.getDiceType().equals(dice)) {
                return true;
            }
        }
        return false;
    }

    public int getXp() {
        return xp;
    }

    public double getLootRate() {
        return lootRate;
    }

    public double getDamageRate() {
        return damageRate;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean checkXP(int value) {
        return this.getXp() >= value;
    }

    public void addDie(Die die) {
        this.dice.add(die);
    }

    public void updateHighestAcc(double acc) {
        if (acc < this.highestAcc) {
            this.highestAcc = acc;
        }
    }

    public void updateTotalHits() {
        this.totalHits++;
    }

    public void updateTotalRolls() {
        this.totalRolls++;
    }

    public void updateMaxTotalDamage(int damage) {
        if (damage > this.maxTotalDamage) {
            this.maxTotalDamage = damage;
        }
    }

    public void updateMaxSingleDamage(int dmg) {
        if (dmg > this.maxSingleDamage) {
            this.maxSingleDamage = dmg;
        }
    }

    public double getHighestAcc() {
        return highestAcc;
    }

    public int getMaxTotalDamage() {
        return maxTotalDamage;
    }

    public int getMaxSingleDamage() {
        return maxSingleDamage;
    }

    public double getTotalAcc() {
        return totalHits / totalRolls * 100;
    }

    public void checkHealthRegen(long time) {
        Log.i("health", "" + this.getCurrentHealth());
        if (((time - this.timeStamp) / 1E9) > 300) {
            int amt = 1 * regenRate.intValue();
            this.gainHealth(amt);
        }
    }

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

