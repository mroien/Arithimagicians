package ics499.arithimagicians;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class DeathActivity extends AppCompatActivity {
    private Player player;
    private int XP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        XP = getIntent.getIntExtra("XP", 0);
        Log.i("Prereward", "Pre XP is " + Integer.toString(player.getXp()));
        player.gainXP(XP);
        Log.i("Postreward", "New XP is " + Integer.toString(player.getXp()));
        TextView xPRewards = (TextView) findViewById(R.id.XPreward);
        xPRewards.setText("You earned " + Integer.toString(XP) + " experience.\n" +
                "Your total is now " + Integer.toString(player.getXp()) + ".");
    }

    public void closeClick(View view){
        Intent mapIntent = new Intent(this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
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
