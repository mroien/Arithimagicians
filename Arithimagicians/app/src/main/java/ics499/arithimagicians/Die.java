package ics499.arithimagicians;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Die implements Serializable {
    private int diceValue;
    private String diceType;



    public Die() {
        this.diceValue = 4;
        this.diceType = "d4";
    }

    public Die(int diceValue, String diceType) {
        this.diceValue = diceValue;
        this.diceType = diceType;
    }

    public int rollDice() {
        Random random = new Random();
        int roll = random.nextInt(this.diceValue) + 1;
        if (roll == 0) {
            roll = 1;
        }
        return roll;
    }

    public String getDiceType() {
        return this.diceType;
    }

    public int getDieCost(){
        return diceValue;
    }

    public int getDiceValue(){ return diceValue; }
}


