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
     * @return double damage produced by character
     */
    private Double calculateDamage() {

        return 0.0;
    }

    /**
     * Subtracts damage from character's current health. If this would take the
     * health below 0, it sets the health to 0 instead.
     * @param damage
     */
    public void takeDamage(int damage) {
        this.currentHealth = this.currentHealth - damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }

    /**
     * Returns character's current health
     * @return
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Calculates and returns the percentage of the character's health left.
     * @return
     */
    public int getPercentHealthLeft() {
        double z = ((double) this.currentHealth / (double) this.totalHealth) * 100;
        return (int) z;
    }

    /**
     * Increases the character's currentHealth by amt. If this would set character's health
     * above the character's totalHealth, it sets currentHealth to totalHealth instead.
     * @param amt
     */
    public void gainHealth(int amt) {
        if (currentHealth < totalHealth) {
            int missingHealth = getTotalHealth() - getCurrentHealth();
            if (missingHealth > amt) {
                currentHealth += amt;
            } else {
                currentHealth += missingHealth;
            }
        }
    }

    /**
     * Returns the character's totalHealth, which is the character's maximum health.
     * @return
     */
    public int getTotalHealth() {
        return totalHealth;
    }

    /**
     * Returns the character's attack value.
     * @return
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Sets the character's totalHealth (maximum health) to health.
     * @param health
     */
    public void setTotalHealth(int health){
        this.totalHealth = health;
    }
}
