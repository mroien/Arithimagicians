package ics499.arithimagicians;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayMap extends AppCompatActivity {
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Before vieweing the map pull in the users last map location so we can load the correct png
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        setContent();
    }

    public void inventoryClicked(View view) {
        Intent invIntent = new Intent(this, InventoryActivity.class);
        invIntent.putExtra("player", player);
        startActivityForResult(invIntent, 100);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        this.player = (Player) data.getSerializableExtra("player");
    }

    public void setContent(){
        String lastMap = player.getLastMap();
        switch(lastMap){
            case "1_1":
                setContentView(R.layout.activity_map1_1);
                break;
            case "1_2":
                setContentView(R.layout.activity_map1_2);
                break;
        }
    }
}
