package ics499.arithimagicians;

/**
 * Created by 2owls on 11/13/2015.
 */
public class D6 extends Die {
    private static int bonus = 0;

    public D6(){
        super(6, "d6");
    }


    public static int getBonus() {
        return bonus;
    }

    public static void incrementBonus(){
        bonus++;
    }

    @Override
    public int rollDice() {
        int roll = super.rollDice();
        roll += bonus;
        return roll;
    }

    public int getBonusCost(){
        return this.getBonus() * this.getBonus() + this.getDiceValue();
    }
}
