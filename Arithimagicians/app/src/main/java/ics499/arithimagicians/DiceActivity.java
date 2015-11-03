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
        switch (view.getId()) {
            case R.id.d4:
                swapDice("d4");
                setResult(104, previous);
                this.finish();
                break;
            case R.id.d6:
                swapDice("d6");
                setResult(106, previous);
                this.finish();
                break;
            case R.id.d8:
                swapDice("d8");
                setResult(108, previous);
                this.finish();
                break;
        }

    }

    /**
     * Swap dice method used to take a dice from the player inventory and put it into the players usedDice array
     *
     * @param diceClicked String value of what dice was clicked
     */
    public void swapDice(String diceClicked) {
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
        int d14 = 0;
        int d16 = 0;
        int d18 = 0;
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
                case "d14":
                    d14++;
                    break;
                case "d16":
                    d16++;
                    break;
                case "d18":
                    d18++;
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



