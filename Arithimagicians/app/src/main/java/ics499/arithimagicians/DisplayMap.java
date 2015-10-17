package ics499.arithimagicians;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

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

    public void button1_1Clicked(View view){
        Intent levelDescripitionEvent = new Intent(this, MapDescriptionActivity.class);
        switch(view.getId()){
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
        }

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

    @Override
    public void onPause(){
        super.onPause();
        ObjectOutput out = null;
        String fileName = "savedGame";
        File saved = new File(getFilesDir(),fileName);

        try{
            out = new ObjectOutputStream(new FileOutputStream(saved));
            out.writeObject(player);
            out.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
