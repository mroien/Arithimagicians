package ics499.arithimagicians;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 11/2/2015.
 */
public class Opponent extends Character implements Serializable{

    Opponent spearman;
    Opponent shaman;
    Opponent grabber;
    Opponent smasher;
    Opponent wizard;

    public static enum Stats{
        GB_SPEARMAN(3, 0, "Gobloid Spearman"),
        GB_SLASHER(4, 1, "Gobloid Slasher"),
        GB_SHAMAN(5, 2, "Gobloid Shaman"),
        SK_GRABBER(4, 1, "Skeletonian Grabber"),
        SK_SMASHER(5, 2, "Skeletonian Smasher"),
        SK_WIZARD(7, 3, "Skeletonian Wizard");

        private final int health;
        private final int attack;
        private final String name;

        Stats(int health, int attack, String name){
            this.health = health;
            this.attack = attack;
            this.name = name;
        }

        public int getHealth() { return health; }
        public int getAttack() { return attack; }
        public String getName() { return name; }
    }

    private boolean frozen;
    private String weakness;
    private String name;
    private ArrayList<String> op;
    private String layoutName;
    private R.drawable background;
    public Opponent(int health, int attack, String name, String layoutName) {
        // Change to actual values
        super(health, attack);
        this.name = name;
        this.layoutName = layoutName;
    }

    public Opponent (Stats stats, String layoutName){
        super(stats.getHealth(), stats.getAttack());
        this.name = stats.getName();
        this.layoutName = layoutName;
    }

    public void setOp(ArrayList<String> opinc){
    this.op = opinc;
    }
    public ArrayList<String> getOp(){
    return this.op;
}
    public String getLayout(){
        return this.layoutName;
    }
    public String getName() { return this.name; }
}
