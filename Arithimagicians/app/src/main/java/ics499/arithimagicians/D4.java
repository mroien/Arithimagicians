package ics499.arithimagicians;

/**
 * Created by 2owls on 11/13/2015.
 */
public class D4 extends Die {
    private static int bonus = 0;

    public D4(){
        super(4, "d4");
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
}
