package ics499.arithimagicians;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        final ListView listview = (ListView) findViewById(R.id.listview);
        final ItemArrayAdapter adapter = new ItemArrayAdapter(this, R.layout.activity_inventory, inventory);
        listview.setAdapter(adapter);
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
        //TextView t = (TextView) findViewById(R.id.healthPotionTextView);
        //t.setText(Integer.toString(healthPotion));
    }

    private class ItemArrayAdapter extends ArrayAdapter<Item>{

        Context context;
        int layoutResourceId;
        ArrayList<Item> items = null;

        private ItemArrayAdapter(Context context, int layoutResourceId, ArrayList<Item> items){
            super(context, layoutResourceId, items);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemHolder holder = new ItemHolder();

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                row.setTag(holder);
            } else {
                holder = (ItemHolder)row.getTag();
            }
            if (items.get(position) != null) {
                Item itemDisplay = items.get(position);
                holder.item = (TextView) row.findViewById(R.id.invtext);
                holder.item.setText(itemDisplay.getName() + " " + itemDisplay.getQuantity());
            }

            return row;

        }
    }

    static class ItemHolder {
        TextView item;
    }
}
