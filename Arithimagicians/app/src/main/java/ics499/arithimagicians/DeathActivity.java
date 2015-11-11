package ics499.arithimagicians;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DeathActivity extends AppCompatActivity {
    private Player player;
    private int XP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        XP = getIntent.getIntExtra("XP", 0);
        Log.i("Prereward", "Pre XP is " + Integer.toString(player.getXp()));
        player.gainXP(XP);
        Log.i("Postreward","New XP is " + Integer.toString(player.getXp()));
    }

    public void closeClick(View view){
        Intent mapIntent = new Intent(this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }
}
