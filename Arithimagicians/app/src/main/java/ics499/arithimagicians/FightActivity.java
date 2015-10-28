package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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
        ImageButton firstRowFirstDice = (ImageButton) findViewById(R.id.firstRowFirstDice);
        Drawable firstRowFirstDiceDraw = firstRowFirstDice.getBackground();
        ImageButton firstRowSecondDice = (ImageButton) findViewById(R.id.firstRowSecondDice);
        ImageButton secondRowFirstDice = (ImageButton) findViewById(R.id.secondRowFirstDice);
        ImageButton secondRowSecondDice = (ImageButton) findViewById(R.id.secondRowSecondDice);

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
        switch(view.getId()){
            case R.id.firstRowFirstDice:
                diceIntent.putExtra("diceLoc", "firstRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.firstRowSecondDice:
                diceIntent.putExtra("diceLoc", "firstRowSecondDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowFirstDice:
                diceIntent.putExtra("diceLoc", "secondRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowSecondDice:
                diceIntent.putExtra("diceLoc", "secondRowSecondDice");
                startActivityForResult(diceIntent, 110);
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 104){
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");

            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d4);
        }
    }

}
