package ics499.arithimagicians;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 11/2/2015.
 */
public class Opponent extends Character implements Serializable {

    Opponent spearman;
    Opponent shaman;
    Opponent grabber;
    Opponent smasher;
    Opponent wizard;

    /**
     * Stats enum handles standardizing opponent statistics
     */
    public static enum Stats {
        GB_SPEARMAN(3, 0, "Gobloid Spearman"),
        GB_SLASHER(4, 1, "Gobloid Slasher"),
        GB_SHAMAN(5, 2, "Gobloid Shaman"),
        SK_GRABBER(4, 1, "Skeletonian Grabber"),
        SK_SMASHER(5, 2, "Skeletonian Smasher"),
        SK_WIZARD(7, 3, "Skeletonian Wizard");

        private final int health;
        private final int attack;
        private final String name;

        Stats(int health, int attack, String name) {
            this.health = health;
            this.attack = attack;
            this.name = name;
        }

        public int getHealth() {
            return health;
        }

        public int getAttack() {
            return attack;
        }

        public String getName() {
            return name;
        }
    }

    private boolean frozen;
    private String weakness;
    private String name;
    private ArrayList<String> op;
    private String layoutName;
    private R.drawable background;

    /**
     * The full constructor allows an opponent to be created that is not set in Stats already
     * @param health
     * @param attack
     * @param name
     * @param layoutName
     */
    public Opponent(int health, int attack, String name, String layoutName) {
        // Change to actual values
        super(health, attack);
        this.name = name;
        this.layoutName = layoutName;
    }

    /**
     * The constructor with Stats allows standard opponents to be created by calling a Stats block
     * from the enum.
     * @param stats
     * @param layoutName
     */
    public Opponent(Stats stats, String layoutName) {
        super(stats.getHealth(), stats.getAttack());
        this.name = stats.getName();
        this.layoutName = layoutName;
    }

    /**
     * Sets operators for this opponent's equations
     * @param opinc
     */
    public void setOp(ArrayList<String> opinc) {
        this.op = opinc;
    }

    /**
     * Returns operators.
     * @return
     */
    public ArrayList<String> getOp() {
        return this.op;
    }

    /**
     * Returns the layout this opponent should use as a background.
     * @return
     */
    public String getLayout() {
        return this.layoutName;
    }

    /**
     * Returns opponent's name.
     * @return
     */
    public String getName() {
        return this.name;
    }
}
