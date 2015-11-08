package ics499.arithimagicians;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Jacob Kinzer on 11/2/2015.
 */
public class Opponent extends Character implements Serializable{
    private boolean frozen;
    private String weakness;
    private String name;
    private ArrayList<String> op;
    private String layoutName;
    public Opponent(int health, int attack, String name, String layoutName) {
        // Change to actual values
        super(health, attack);
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
}
