package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class DiceActivity extends Activity {
    private Player player;
    private ArrayList<Die> dice;
    private String diceLoc;
    private ArrayList<Die> diceUsed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        diceUsed = player.getDiceUsed();
        diceLoc = getIntent.getStringExtra("diceLoc");
        generateDice();
        setDiceCount();
    }

    public void closeClick(View view) {
        Intent previous = new Intent(getApplicationContext(), FightActivity.class);
        previous.putExtra("player", this.player);
        setResult(100, previous);
        this.finish();
    }

    public void selectDice(View view) {
        Intent previous = new Intent(getApplicationContext(), FightActivity.class);
        previous.putExtra("player", this.player);
        previous.putExtra("diceSelected", view.getId());
        previous.putExtra("diceLoc", diceLoc);
        switch(view.getId()){
            case R.id.d4:
                swapDice("D4");
                setResult(104, previous);
                this.finish();
                break;
            case R.id.d6:
                swapDice("D6");
                setResult(106, previous);
                this.finish();
                break;
            case R.id.d8:
                swapDice("D8");
                setResult(108, previous);
                this.finish();
                break;
        }

    }

    public void swapDice(String diceClicked){
        for(Die d : dice){
            if(d.getDiceType().equals(diceClicked)){
                dice.remove(d);
                diceUsed.add(d);
                break;
            }
        }
    }

    public void generateDice() {
        dice = player.getDice();
    }

    public void setDiceCount() {
        int d4 = 0;
        int d6 = 0;
        int d8 = 0;
        int d10 = 0;
        int d12 = 0;
        int d14 = 0;
        int d16 = 0;
        int d18 = 0;
        int d20 = 0;
        for (Die d : dice) {
            switch (d.getDiceType()) {
                case "D4":
                    d4++;
                    break;
                case "D6":
                    d6++;
                    break;
                case "D8":
                    d8++;
                    break;
                case "D10":
                    d10++;
                    break;
                case "D12":
                    d12++;
                    break;
                case "D14":
                    d14++;
                    break;
                case "D16":
                    d16++;
                    break;
                case "D18":
                    d18++;
                    break;
                case "D20":
                    d20++;
                    break;

            }
        }

        TextView t = (TextView) findViewById(R.id.d4TextView);
        t.setText(Integer.toString(d4));
         t = (TextView) findViewById(R.id.d6TextView);
        t.setText(Integer.toString(d6));
         t = (TextView) findViewById(R.id.d8TextView);
        t.setText(Integer.toString(d8));

    }

}



