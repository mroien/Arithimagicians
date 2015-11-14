package ics499.arithimagicians;

/**
 * Created by Wiggles on 10/15/2015.
 */
public class PowerUpItem extends Item {
    private int transID;
    public PowerUpItem(String name, String bonus, int quantity)
    {
        super(name, bonus, quantity);
    }

    public PowerUpItem(Type type, String bonus, int quantity){
        super(type.getName(), bonus, quantity);


    }

}
