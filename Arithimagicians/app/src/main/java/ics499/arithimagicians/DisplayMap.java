package ics499.arithimagicians;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Before vieweing the map pull in the users last map location so we can load the correct png
        setContentView(R.layout.activity_map1_1);
    }

    public void inventoryClicked(View view){
        Intent invIntent = new Intent(this, InventoryActivity.class);
        startActivity(invIntent);

    }
}
