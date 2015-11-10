package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Inventory activity to display the inventory of the user
 */
public class InventoryActivity extends Activity {
    private int healthPotionCount;
    private Player player;
    private ArrayList<Item> inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        setContentView(R.layout.activity_inventory);
        inventory = player.getInventory();
        setInventoryCount();


    }

    /**
     * Use item 1 in the inventory click handler. Decerement the amt of item in the inventory and the set the player inventory
     *
     * @param view View of the dialog
     */
    public void useHealthPotion(View view) {
        inventory.get(0).decrementValue();
        setInventoryCount();
    }

    /**
     * Close button clicked. Create a previous intent to go back to the map class, put the player object in there, then start it
     *
     * @param view View of the dialog
     */
    public void closeClick(View view) {
        player.setInventory(inventory);
        Intent previous = new Intent(getApplicationContext(), DisplayMap.class);
        previous.putExtra("player", this.player);
        setResult(100, previous);
        this.finish();
    }

    /**
     * Method to set the count of the inventory of the player
     */
    public void setInventoryCount() {
        int healthPotion = inventory.get(0).getQuantity();
        TextView t = (TextView) findViewById(R.id.healthPotionTextView);
        t.setText(Integer.toString(healthPotion));
    }
}