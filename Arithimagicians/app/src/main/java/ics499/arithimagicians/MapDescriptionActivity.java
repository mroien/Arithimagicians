package ics499.arithimagicians;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class MapDescriptionActivity extends Activity {
    private Player player;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        level = getIntent.getStringExtra("level");

        setContentView(R.layout.activity_map_descripition);
        setDesc(level);


    }

    public void closeClick(View view){
        this.finish();
    }

    public void fightClick(View view)
    {
        Intent fightIntent = new Intent(this, FightActivity.class);
        fightIntent.putExtra("player", player);
        fightIntent.putExtra("level", level);
        startActivity(fightIntent);
    }

    public void setDesc(String level)
    {
        TextView t = (TextView) findViewById(R.id.mapDescTextView);
        switch(level){
            case "1_1":
                t.setText(getResources().getString(R.string.Level1_1));
                break;
            case "1_2":
                t.setText(getResources().getString(R.string.Level1_2));
                break;
            case "1_3":
                t.setText(getResources().getString(R.string.Level1_3));
                break;
            case "1_4":
                t.setText(getResources().getString(R.string.Level1_4));
                break;
            case "1_5":
                t.setText(getResources().getString(R.string.Level1_5));
                break;
            case "2_1":
                t.setText(getResources().getString(R.string.Level2_1));
                break;
            case "2_2":
                t.setText(getResources().getString(R.string.Level2_2));
                break;
            case "2_3":
                t.setText(getResources().getString(R.string.Level2_3));
                break;
            case "2_4":
                t.setText(getResources().getString(R.string.Level2_4));
                break;
            case "2_5":
                t.setText(getResources().getString(R.string.Level2_5));
                break;
            case "3_1":
                t.setText(getResources().getString(R.string.Level3_1));
                break;
            case "3_2":
                t.setText(getResources().getString(R.string.Level3_2));
                break;
            case "3_3":
                t.setText(getResources().getString(R.string.Level3_3));
                break;
            case "3_4":
                t.setText(getResources().getString(R.string.Level3_4));
                break;
            case "3_5":
                t.setText(getResources().getString(R.string.Level3_5));
                break;


        }
    }




}
