package ics499.arithimagicians;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Die implements Serializable{
    private int diceValue;
    private String diceType;
    private int bonusDamage;
    private String element;

    public Die() {
        this.diceValue = 4;
        this.diceType = "D4";
        this.bonusDamage = 0;
        this.element = "";
    }

    public Die(int diceValue, String diceType, int bonusDamage) {
        this.diceValue = diceValue;
        this.diceType = diceType;
        this.bonusDamage = bonusDamage;
    }

    public void setBonusDamage(int bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public int rollDice() {
        Random random = new Random();
        int roll = random.nextInt(this.diceValue - 1) + 1;
        return roll;
    }

    public String getDiceType(){ return this.diceType; }

    public void setElement(String ele){
        this.element = ele;
    }}
