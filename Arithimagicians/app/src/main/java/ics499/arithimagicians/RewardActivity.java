package ics499.arithimagicians;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Random;

public class RewardActivity extends AppCompatActivity {
    private Player player;
    private String level;
    private int XP;
    private final static double LOOTRATE = 0.25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        level = getIntent.getStringExtra("level");
        XP = getIntent.getIntExtra("XP", 0);
        setNewLevel();
        Log.i("Prereward", "Pre XP is " + Integer.toString(player.getXp()));
        player.gainXP(XP);
        Log.i("Postreward", "New XP is " + Integer.toString(player.getXp()));
        TextView xPRewards = (TextView) findViewById(R.id.XPreward);
        xPRewards.setText("You earned " + Integer.toString(XP) + " experience.\n" +
                "Your total is now " + Integer.toString(player.getXp()) + ".");
        Item loot = checkLootReward();
        if(loot != null){
            TextView lootReward = (TextView)findViewById(R.id.lootReward);
            lootReward.setText("You gained a " + loot.getName() + " as well.");
        }
    }

    public Item checkLootReward(){
        Random random = new Random();
        float roll = random.nextFloat();
        System.out.println(random);
        if(roll > LOOTRATE * player.getLootRate()){
            Item loot = new Item(Item.Type.HEALTHPOTION.getName(), "+5", 1);
            player.addItem(loot);
            return loot;
        }
        return null;
    }

    public void closeClick(View view){
        Intent mapIntent = new Intent(this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }

    public void setNewLevel(){
        switch(level){
            case "1_1":
                if(player.getLastMap().equalsIgnoreCase("1_1")) {
                    player.setLevel("1_2");
                }
                break;
            case "1_2":
                if(player.getLastMap().equalsIgnoreCase("1_2")) {
                    player.setLevel("1_3");
                }
                break;
            case "1_3":
                if(player.getLastMap().equalsIgnoreCase("1_3")) {
                    player.setLevel("1_4");
                }
                break;
            case "1_4":
                if(player.getLastMap().equalsIgnoreCase("1_4")) {
                    player.setLevel("1_5");
                }
                break;
            case "1_5":
                if(player.getLastMap().equalsIgnoreCase("1_5")) {
                    player.setLevel("2_1");
                }
                break;
            case "2_1":
                if(player.getLastMap().equalsIgnoreCase("2_1")) {
                    player.setLevel("2_2");
                }
                break;
            case "2_2":
                if(player.getLastMap().equalsIgnoreCase("2_2")) {
                    player.setLevel("2_3");
                }
                break;
            case "2_3":
                if(player.getLastMap().equalsIgnoreCase("2_3")) {
                    player.setLevel("2_4");
                }
                break;
            case "2_4":
                if(player.getLastMap().equalsIgnoreCase("2_4")) {
                    player.setLevel("2_5");
                }
                break;
            case "2_5":
                if(player.getLastMap().equalsIgnoreCase("2_5")) {
                    player.setLevel("3_1");
                }
                break;
            case "3_1":
                if(player.getLastMap().equalsIgnoreCase("3_1")) {
                    player.setLevel("3_2");
                }
                break;
            case "3_2":
                if(player.getLastMap().equalsIgnoreCase("3_2")) {
                    player.setLevel("3_3");
                }
                break;
            case "3_3":
                if(player.getLastMap().equalsIgnoreCase("3_3")) {
                    player.setLevel("3_4");
                }
                break;
            case "3_4":
                if(player.getLastMap().equalsIgnoreCase("3_4")) {
                    player.setLevel("3_5");
                }
                break;

        }

    }
    @Override
    public void onPause() {
        super.onPause();
        ObjectOutput out = null;
        String fileName = "savedGame";
        File saved = new File(getFilesDir(), fileName);

        try {
            out = new ObjectOutputStream(new FileOutputStream(saved, false));
            out.writeObject(player);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ObjectOutput out = null;
        String fileName = "savedGame";
        File saved = new File(getFilesDir(), fileName);

        try {
            out = new ObjectOutputStream(new FileOutputStream(saved, false));
            out.writeObject(player);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
