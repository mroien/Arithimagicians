package ics499.arithimagicians;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;

public class RewardActivity extends AppCompatActivity {
    private Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
    }
    public void closeClick(View view){
        Intent mapIntent = new Intent(this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }

}
