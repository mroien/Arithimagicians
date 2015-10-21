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
        }
    }




}
