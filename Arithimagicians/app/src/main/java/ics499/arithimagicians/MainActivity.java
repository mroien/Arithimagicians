package ics499.arithimagicians;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog.*;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        read();
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
        if (this.player == null) {
            Builder builder = new Builder(this);
            builder
                    .setTitle("No Account Found")
                    .setMessage("Would you like to connect your account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            player = new Player();
                            Intent connectIntent = new Intent(MainActivity.this, ConnectAccount.class);
                            connectIntent.putExtra("player", player);
                            startActivity(connectIntent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Get the mobile phone number, call the db and see if it matches an account from the website, if it does, tie them together with a rest call

                            // Create user account without any information besides random accountId, and set level to the start
                            player = new Player();
                            // Send to start screen:
                            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);


                        }
                    }).show();
        }
        else{
            Builder builder = new Builder(this);
            builder
                    .setTitle("Game Found")
                    .setMessage("Are you sure you want to start a new game? (This will delete your old game!)")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            player = new Player();
                            Intent connectIntent = new Intent(MainActivity.this, ConnectAccount.class);
                            connectIntent.putExtra("player", player);
                            startActivity(connectIntent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        finish();
        }
    }).show();
        }}

    /**
     * Handler for loading a game
     *
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
            this.player = (Player) input.readObject();
            input.close();
            getItems(player.getUserId());

        } catch (StreamCorruptedException e) {
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
        } catch (ClassNotFoundException e) {
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
        }

    }

    public void getItems(int userId) {
        String url = "http://192.168.29.115:8080/checkPowerUp?accountId=" + userId;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("No Powerups")) {
                            List<String> powerUps = Arrays.asList(response.split(","));
                            for (String s : powerUps) {

                                Item.Type itemType = Item.findType(s);
                                PowerUpItem item = new PowerUpItem(itemType, itemType.getBonus(), 1);
                                if (player != null)
                                    player.addItem(item);
                                updateDateInDB(s);

                            }
                            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);
                        } else {
                            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void updateDateInDB(String powerup) {
        String url = "http://192.168.29.115:8080/powerUpAddedToInv?accountId=" + player.getUserId() + "&powerUpName=" + powerup;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("User Updated")) {

                        } else {
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
