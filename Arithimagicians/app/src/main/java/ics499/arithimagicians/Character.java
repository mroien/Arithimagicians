package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 */
public class Character implements Serializable{
    private int health;
    private int attack;
    public Character(int health, int attack){
        this.health = health;
        this.attack = attack;
    }

    private Double calculateDamage(){

        return 0.0;
    }
}
