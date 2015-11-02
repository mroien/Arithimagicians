package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 * Main Charcacter super class for opponents and players
 */
public class Character implements Serializable {
    private int health;
    private int attack;

    /**
     * Constructor for creating a Character
     *
     * @param health int health number of the character
     * @param attack int attack number of the character
     */
    public Character(int health, int attack) {
        this.health = health;
        this.attack = attack;
    }

    /**
     * Method for a character to calculate how much damage they perform
     *
     * @return double damage produced by character
     */
    private Double calculateDamage() {

        return 0.0;
    }
}
