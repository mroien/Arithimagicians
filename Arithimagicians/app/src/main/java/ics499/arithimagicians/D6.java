package ics499.arithimagicians;

/**
 * Created by 2owls on 11/13/2015.
 */
public class D6 extends Die {
    private static int bonus = 0;
    /**
     * Calls Die's constructor to set create a Die with a value of 6 and a type of "d6"
     */
    public D6(){
        super(6, "d6");
    }
    /**
     * Returns the bonus for all D6s.
     * @return
     */
    public static int getBonus() {
        return bonus;
    }

    /**
     * Increments the bonus for all D6s.
     */
    public static void incrementBonus() {
        bonus++;
    }

    /**
     * Overrides Die's rollDice method to correctly calculate the bonus from all D6s.
     * @return
     */
    @Override
    public int rollDice() {
        int roll = super.rollDice();
        roll += bonus;
        return roll;
    }

    /**
     * Returns the cost of purchasing the next level of bonus for all D6s.
     * @return
     */
    public int getBonusCost() {
        return this.getBonus() * this.getBonus() + this.getDiceValue();
    }
}
