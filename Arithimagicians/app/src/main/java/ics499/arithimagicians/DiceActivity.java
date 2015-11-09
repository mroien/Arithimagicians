package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Dice activity class that pops up when a user selects to use a dice in a fight activity
 */
public class DiceActivity extends Activity {
    private Player player;
    private ArrayList<Die> dice;
    private String diceLoc;
    private ArrayList<Die> diceUsed;
    private String currentElement;
    private boolean filled;
    private String diceFilled;
    private ArrayList<String> opList;
    private ArrayList<Opponent> opponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        diceUsed = player.getDiceUsed();
        diceLoc = getIntent.getStringExtra("diceLoc");
        filled = getIntent.getBooleanExtra("alreadyFilled", false);
        diceFilled = getIntent.getStringExtra("diceFilled");
        opList = getIntent.getStringArrayListExtra("opList");
        opponents = (ArrayList<Opponent>) getIntent.getSerializableExtra("opponents");
        generateDice();
        setDiceCount();

    }

    /**
     * Performed when the close button is clicked on the dialog
     * Create the new intent that is going back to the fight activity
     * Put the player object into the intent so that we can keep variables between intents
     * Send a result of 100, meaning close click and finish this intent
     *
     * @param view View of the dialog
     */
    public void closeClick(View view) {
        Intent previous = new Intent(getApplicationContext(), FightActivity.class);
        previous.putExtra("player", this.player);
        setResult(100, previous);
        this.finish();
    }



    /**
     * Select dice method that is used when a dice in the dice dialog is clicked
     * Create a new intent to go back to the fight activity, put in the player object, which dice is selected, and the location that was selected from the fight activity
     * Switch off which dice was clicked, if D4 we swap the D4 out of the inventory and put it into the diceUsed array to signify it is being used
     * Then set the result to the dice number, eg 104 for D4, then finish the dialog
     *
     * @param view View of the dialog
     */
    public void selectDice(View view) {
        Intent previous = new Intent(getApplicationContext(), FightActivity.class);
        previous.putExtra("player", this.player);
        previous.putExtra("diceSelected", view.getId());
        previous.putExtra("diceLoc", diceLoc);
        previous.putExtra("element", currentElement );
        previous.putExtra("opList", opList);
        previous.putExtra("opponents", opponents);
        switch (view.getId()) {
            case R.id.d4:
                if(player.checkDice("d4")) {
                    swapDice("d4");
                    setResult(104, previous);
                    this.finish();
                }
                break;
            case R.id.d6:
                if(player.checkDice("d6")) {
                    swapDice("d6");
                    setResult(106, previous);
                    this.finish();
                }
                break;
            case R.id.d8:
                if(player.checkDice("d8")) {
                    swapDice("d8");
                    setResult(108, previous);
                    this.finish();
                }
                break;
            case R.id.d10:
                if(player.checkDice("d10")) {
                    swapDice("d10");
                    setResult(110, previous);
                    this.finish();
                }
                break;
            case R.id.d12:
                if(player.checkDice("d10")) {
                    swapDice("d12");
                    setResult(112, previous);
                    this.finish();
                }
                break;
            case R.id.d20:
                if(player.checkDice("d20")) {
                    swapDice("d20");
                    setResult(120, previous);
                    this.finish();
                }
                break;
        }

    }

    /**
     * Swap dice method used to take a dice from the player inventory and put it into the players usedDice array
     *
     * @param diceClicked String value of what dice was clicked
     */
    public void swapDice(String diceClicked) {
       if(filled == true){
           Die temp;
           for(Die d : diceUsed){
               if(d.getDiceType().equals(diceFilled)){
                   temp = d;
                   diceUsed.remove(d);
                   dice.add(d);
                   break;
               }
           }

       }

        for (Die d : dice) {
            if (d.getDiceType().equals(diceClicked)) {
                d.setElement(currentElement);
                dice.remove(d);
                diceUsed.add(d);
                break;
            }
        }
    }

    public void selectEle(View view)
    {
        CheckBox checkIce  = (CheckBox) findViewById(R.id.iceCheck);
        CheckBox checkLight  = (CheckBox) findViewById(R.id.lightCheck);
        CheckBox checkFire  = (CheckBox) findViewById(R.id.fireCheck);

        switch(view.getId()){
            case R.id.ice:
                currentElement = "Ice";
                checkIce.setChecked(true);
                checkLight.setChecked(false);
                checkFire.setChecked(false);
                break;
            case R.id.lightning:
                currentElement = "Light";
                checkIce.setChecked(false);
                checkLight.setChecked(true);
                checkFire.setChecked(false);
                break;
            case R.id.fire:
                currentElement = "Fire";
                checkIce.setChecked(false);
                checkLight.setChecked(false);
                checkFire.setChecked(true);
                break;
        }
    }

    /**
     * Generate dice method to populate dice array with the players available dice
     */
    public void generateDice() {
        dice = player.getDice();
    }

    /**
     * Method to set the dice textfields to state how many dice of each the player has
     */
    public void setDiceCount() {
        int d4 = 0;
        int d6 = 0;
        int d8 = 0;
        int d10 = 0;
        int d12 = 0;
        int d20 = 0;
        for (Die d : dice) {
            switch (d.getDiceType()) {
                case "d4":
                    d4++;
                    break;
                case "d6":
                    d6++;
                    break;
                case "d8":
                    d8++;
                    break;
                case "d10":
                    d10++;
                    break;
                case "d12":
                    d12++;
                    break;
                case "d20":
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



