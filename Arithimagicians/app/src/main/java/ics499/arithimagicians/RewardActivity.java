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

public class RewardActivity extends AppCompatActivity {
    private Player player;
    private String level;
    private int XP;
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

}
