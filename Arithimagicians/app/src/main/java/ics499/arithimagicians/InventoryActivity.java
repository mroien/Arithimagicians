package ics499.arithimagicians;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Inventory activity to display the inventory of the user
 */
public class InventoryActivity extends Activity {
    private int healthPotionCount;
    private Player player;
    private ArrayList<Item> inventory;
    private ArrayList<TextView> invCounts = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        setContentView(R.layout.activity_inventory);
        inventory = player.getInventory();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gridWrapper);
        LinearLayout rowOne = (LinearLayout) findViewById(R.id.rowOne);
        LinearLayout rowTwo = (LinearLayout) findViewById(R.id.rowTwo);
        for (int i = 0; i < Item.Type.LAST.ordinal(); i++){
            final Item item = inventory.get(i);
            if (item  != null){
                LinearLayout btnTotalWrap = new LinearLayout(this);
                btnTotalWrap.setOrientation(LinearLayout.VERTICAL);
                btnTotalWrap.setGravity(Gravity.CENTER_HORIZONTAL);

                Button btn = new Button(this);
                btn.setText(item.getName());

                btnTotalWrap.addView(btn);

                TextView itemCount = new TextView(this);
                itemCount.setText(Integer.toString(item.getQuantity()));
                itemCount.setGravity(Gravity.CENTER_HORIZONTAL);
                itemCount.setTextColor(Color.BLACK);
                invCounts.add(itemCount);
                itemCount.setId(invCounts.indexOf(itemCount));
                btnTotalWrap.addView(itemCount);

                btn.setOnClickListener(new MyOnClickListener(item, itemCount.getId()));

                if (i <= Item.Type.LAST.ordinal() / 2){
                    rowOne.addView(btnTotalWrap);
                } else {
                    rowTwo.addView(btnTotalWrap);
                }
            }
        }

        //setInventoryCount();
    }

    /**
     * Use item 1 in the inventory click handler. Decerement the amt of item in the inventory and the set the player inventory
     *
     * @param view View of the dialog

    public void useHealthPotion(View view) {
        inventory.get(0).decrementValue();
        setInventoryCount();
    } */

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
/*    public void setInventoryCount() {
        int healthPotion = inventory.get(0).getQuantity();
        //TextView t = (TextView) findViewById(R.id.healthPotionTextView);
        //t.setText(Integer.toString(healthPotion));
    } */

    public class MyOnClickListener implements View.OnClickListener {

        Item itemUsed;
        int index;


        public MyOnClickListener(Item item, int i) {
            this.itemUsed = item;
            this.index = i;
        }

        @Override
        public void onClick(View arg0) {
            player.useItem(itemUsed);
            invCounts.get(index).setText(Integer.toString(itemUsed.getQuantity()));
        }
    }
}