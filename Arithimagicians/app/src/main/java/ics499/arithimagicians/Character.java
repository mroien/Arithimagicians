package ics499.arithimagicians;

import java.io.Serializable;

/**
 * Created by Jacob Kinzer on 10/15/2015.
 * Main Charcacter super class for opponents and players
 */
public class Character implements Serializable {
    private int currentHealth;
    private int attack;
    private int totalHealth;

    /**
     * Constructor for creating a Character
     *
     * @param currentHealth int currentHealth number of the character
     * @param attack        int attack number of the character
     */
    public Character(int currentHealth, int attack) {
        this.currentHealth = currentHealth;
        this.attack = attack;
        this.totalHealth = currentHealth;
    }

    /**
     * Method for a character to calculate how much damage they perform
     *
     * @return double damage produced by character
     */
    private Double calculateDamage() {

        return 0.0;
    }

    public void takeDamage(int damage) {
        this.currentHealth = this.currentHealth - damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public int getPercentHealthLeft() {
        double z = ((double) this.currentHealth / (double) this.totalHealth) * 100;
        return (int) z;
    }

    public void gainHealth(int amt) {
        currentHealth += amt;
    }

    public int getTotalHealth() {
        return totalHealth;
    }

    public int getAttack() {
        return attack;
    }
}
