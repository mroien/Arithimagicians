package ics499.arithimagicians;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Die {
    private int diceValue;
    private String diceType;
    private int bonusDamage;

    public Die() {
        this.diceValue = 1;
        this.diceType = "D4";
        this.bonusDamage = 0;
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
        return 0;
    }
}
