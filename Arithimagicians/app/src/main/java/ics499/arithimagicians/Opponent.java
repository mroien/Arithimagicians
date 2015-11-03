package ics499.arithimagicians;

import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 11/2/2015.
 */
public class Opponent extends Character{
    private boolean frozen;
    private String weakness;
    private String name;
    private ArrayList<String> op;
    public Opponent(int health, int attack, String name) {
        // Change to actual values
        super(health, attack);

    }

public void setOp(ArrayList<String> opinc){
    this.op = opinc;
    }
public ArrayList<String> getOp(){
    return this.op;
}
}
