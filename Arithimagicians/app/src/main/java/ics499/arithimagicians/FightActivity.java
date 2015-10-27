package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Jacob Kinzer on 10/20/2015.
 */
public class FightActivity extends AppCompatActivity {
    private Player player;
    private String level;
    private String firstRowOp;
    private String secondRowOp;
    private String firstRowAns;
    private String secondRowAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        level = getIntent.getStringExtra("level");
        setContentView(R.layout.activity_fight);
        setContent();
    }

    public void attackClicked(View view)
    {
        String firstRowFirstDice;
        String firstRowSecondDice;
        String secondRowFirstDice;
        String secondRowSecondDice;

    }
    public void inventoryClicked(View view) {
        Intent invIntent = new Intent(this, InventoryActivity.class);
        invIntent.putExtra("player", player);
        startActivityForResult(invIntent, 100);

    }

    public void setContent() {
        String lastMap = player.getLastMap();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.fightLayout);
        TextView player = (TextView) findViewById(R.id.playerHealthTextView);
        TextView op = (TextView) findViewById(R.id.oppTextView);
        switch (level) {
            case "1_1":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidspearman));
                player.setText("Player HP : 10");
                op.setText("Opponent HP: 3");
                break;
            case "1_2":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidslasher));
                player.setText("Player HP : 10");
                op.setText("Opponent HP: 4");
                break;
        }
    }

    public void diceClicked(View view){
        Intent diceIntent = new Intent(this, DiceActivity.class);
        diceIntent.putExtra("player", player);
        startActivityForResult(diceIntent, 100);
    }

}
