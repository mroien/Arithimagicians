package ics499.arithimagicians;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Main activity class of the application
 */
public class MainActivity extends AppCompatActivity implements Serializable {
    private boolean splash = false;
    private String account;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Handler for new game button clicked
     * If the player does not have a saved account, create a pop up dialog asking to make an account, then send to the website.
     * Otherwise create a new player, and send the activity to the display map activity
     *
     * @param view View of the activity
     */
    public void newGameClicked(View view) {
        // if no account found, ask if they want to make an account and display the webpage
        if (account == null) {
            Builder builder = new Builder(this);
            builder
                    .setTitle("No Account Found")
                    .setMessage("Would you like to make an account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, do something

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Create user account without any information besides random accountId, and set level to the start
                            player = new Player();
                            // Send to start screen:
                            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);


                        }
                    }).show();
        }
    }

    /**
     * Handler for loading a game
     * @param view
     */
    public void loadGameClicked(View view) {
        read();
    }

    /**
     * Find the saved game object and read in the player object. Create a displaymap intent and put the player object into it
     * Start the activity
     */
    public void read() {
        ObjectInputStream input;
        String filename = "savedGame";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(), "") + File.separator + filename)));
            Player player = (Player) input.readObject();
            input.close();
            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
            mapIntent.putExtra("player", player);
            startActivity(mapIntent);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("No Game Found")
                    .setMessage("No saved game was found")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
