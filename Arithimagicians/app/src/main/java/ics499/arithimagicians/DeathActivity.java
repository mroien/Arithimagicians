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

/**
 * DeathActivity extends AppCompatActivity. It creates the post fight screen
 * when the player's hit points are taken to 0 during a fight.
 */
public class DeathActivity extends AppCompatActivity {
    private Player player;
    private int XP;

    /**
     * Overrides parent's onCreate() method. Calls super.onCreate with savedInstanceState Bundle
     * and sets the screen, player, xp, and message on the screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        XP = getIntent.getIntExtra("XP", 0);
        player.gainXP(XP);
        TextView xPRewards = (TextView) findViewById(R.id.XPreward);
        xPRewards.setText("You earned " + Integer.toString(XP) + " experience.\n" +
                "Your total is now " + Integer.toString(player.getXp()) + ".");
    }

    /**
     * Called when user clicks the 'Close' button. Returns the game to the DisplayMap
     * activity and passes player.
     * @param view
     */
    public void closeClick(View view) {
        Intent mapIntent = new Intent(this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }

    /**
     * Overrides parent's onPause() method. Saves the game into a file when the
     * app loses focus.
     */
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

    /**
     * Overrides parent's onStop method. Saves the game when the app is shut down.
     */
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
