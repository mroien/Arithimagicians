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

    /**
     * Constructor. Takes an int for the "value" or number of sides the die has and a string
     * in the form of "d#" to indicate the die type where # also equals the number of sides
     * on the die.
     * @param diceValue
     * @param diceType
     */
    public Die(int diceValue, String diceType) {
        this.diceValue = diceValue;
        this.diceType = diceType;
    }

    /**
     * Returns an int indicating the value that the die rolled. The value will be between 1 and
     * diceValue, inclusive.
     * @return
     */
    public int rollDice() {
        Random random = new Random();
        int roll = random.nextInt(this.diceValue) + 1;
        if (roll == 0) {
            roll = 1;
        }
        return roll;
    }

    /**
     * Returns the string indicating the die type.
     * @return
     */
    public String getDiceType() {
        return this.diceType;
    }

    /**
     * Returns the cost of purchasing the die. The cost equals the number of sides the die has.
     * @return
     */
    public int getDieCost() {
        return diceValue;
    }

    /**
     * Returns the number of sides the die has.
     * @return
     */
    public int getDiceValue() {
        return diceValue;
    }
}


