package ics499.arithimagicians;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Display map activity that shows what location a player can fight on
 */
public class DisplayMap extends AppCompatActivity {
    private Player player;
    private ProgressBar playerProgressBar;
    private TextView playerHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Before vieweing the map pull in the users last map location so we can load the correct png
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        setContent();
        this.player.checkHealthRegen(System.nanoTime());
        setHealthBar();

    }

    /**
     * Inventory clicked method that will be used when a player clicks the inventory icon
     * Create the new inventory intent, put the player object into it, and start the activity for a result,
     * so we know which powerup or consumable they used
     *
     * @param view
     */
    public void inventoryClicked(View view) {
        setHealthBar();
        Intent invIntent = new Intent(this, InventoryActivity.class);
        invIntent.putExtra("player", player);
        startActivityForResult(invIntent, 100);
        this.player.checkHealthRegen(System.nanoTime());
        this.player.checkPowerupTimer(System.nanoTime());
    }

    /**
     * Sets the health bar based on the percentage of health the player has left.
     */
    public void setHealthBar(){
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
        playerHealth.setText("Player HP : " + player.getCurrentHealth());
        playerProgressBar.setProgress(player.getPercentHealthLeft());
    }

    /**
     * Used when a fight location is clicked. Create the level descripition event then switch off which location was clicked
     * Put in the player object and put which level was clicked into the intent
     */
    public void button1_1Clicked(View view) {
        Intent levelDescripitionEvent = new Intent(this, MapDescriptionActivity.class);
        this.player.checkHealthRegen(System.nanoTime());
        this.player.checkPowerupTimer(System.nanoTime());
        if (this.player.getCurrentHealth() <= 0) {
            Intent healthLow = new Intent(this, HealthTooLowActivity.class);
            healthLow.putExtra("player", player);
            startActivityForResult(healthLow, 100);
        } else
            switch (view.getId()) {
                case R.id.button1_1:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "1_1");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button1_2:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "1_2");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button1_3:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "1_3");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button1_4:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "1_4");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button1_5:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "1_5");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button2_1:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "2_1");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button2_2:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "2_2");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button2_3:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "2_3");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button2_4:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "2_4");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button2_5:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "2_5");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button3_1:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "3_1");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button3_2:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "3_2");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button3_3:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "3_3");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button3_4:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "3_4");
                    startActivity(levelDescripitionEvent);
                    break;
                case R.id.button3_5:
                    levelDescripitionEvent.putExtra("player", player);
                    levelDescripitionEvent.putExtra("level", "3_5");
                    startActivity(levelDescripitionEvent);
                    break;

            }

    }

    /**
     * Overrides parent's onActivityResult. Sets the health bar and stores the player passed into
     * the method.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.player = (Player) data.getSerializableExtra("player");
        setHealthBar();
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Set content method to display which map to show based of what location a player has last beat
     */
    public void setContent() {
        String lastMap = player.getLastMap();
        switch (lastMap) {
            case "1_1":
                setContentView(R.layout.activity_map1_1);
                break;
            case "1_2":
                setContentView(R.layout.activity_map1_2);
                break;
            case "1_3":
                setContentView(R.layout.activity_map1_3);
                break;
            case "1_4":
                setContentView(R.layout.activity_map1_4);
                break;
            case "1_5":
                setContentView(R.layout.activity_map1_5);
                break;
            case "2_1":
                setContentView(R.layout.activity_map2_1);
                break;
            case "2_2":
                setContentView(R.layout.activity_map2_2);
                break;
            case "2_3":
                setContentView(R.layout.activity_map2_3);
                break;
            case "2_4":
                setContentView(R.layout.activity_map2_4);
                break;
            case "2_5":
                setContentView(R.layout.activity_map2_5);
                break;
            case "3_1":
                setContentView(R.layout.activity_map3_1);
                break;
            case "3_2":
                setContentView(R.layout.activity_map3_2);
                break;
            case "3_3":
                setContentView(R.layout.activity_map3_3);
                break;
            case "3_4":
                setContentView(R.layout.activity_map3_4);
                break;
            case "3_5":
                setContentView(R.layout.activity_map3_5);
                break;
        }
        setHealthBar();
    }

    /**
     * Overrides parent's onPause method. Saves the game when the app loses focus.
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

    public void getItems(int userId) {
        String url = "http://52.32.43.132:8080/checkPowerUp?accountId=" + userId;
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
                            Intent mapIntent = new Intent(DisplayMap.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);
                        } else {
                            Intent mapIntent = new Intent(DisplayMap.this, DisplayMap.class);
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
        String url = "http://52.32.43.132:8080/powerUpAddedToInv?accountId=" + player.getUserId() + "&powerUpName=" + powerup;
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
