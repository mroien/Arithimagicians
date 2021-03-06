package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * DiceRollResults extends Activity and handles displaying the results of a dice roll/attack
 * to the user.
 */
public class DiceRollResults extends Activity {
    private Player player;
    private String level;
    private ArrayList<String> opList;
    private ArrayList<Opponent> opponents;
    private int firstRowFirstDice;
    private int firstRowSecondDice;
    private int secondRowFirstDice;
    private int secondRowSecondDice;
    private String firstRowOpString;
    private String secondRowOpString;
    private String firstRowSecondOpString;
    private String secondRowSecondOpString;
    private String firstRowAnsString;
    private String secondRowAnsString;
    private String firstRowUserString;
    private int firstRowAttack;
    private String secondRowUserString;
    private int secondRowAttack;
    private boolean isDeadOnFirstRoll;
    private boolean isDead;
    private double opAttack;

    /**
     * Overrides parent's onCreate method. Runs when the activity creates and stores all of the
     * die rolls, opponents and operands found in savedInstanceState.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll_results);

        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        level = getIntent.getStringExtra("level");
        opList = (ArrayList<String>) getIntent.getSerializableExtra("opList");
        opponents = (ArrayList<Opponent>) getIntent.getSerializableExtra("opponents");
        firstRowFirstDice = getIntent.getIntExtra("firstRowFirstDice", 0);
        firstRowSecondDice = getIntent.getIntExtra("firstRowSecondDice", 0);
        secondRowFirstDice = getIntent.getIntExtra("secondRowFirstDice", 0);
        secondRowSecondDice = getIntent.getIntExtra("secondRowSecondDice", 0);
        firstRowOpString = getIntent.getStringExtra("firstRowOp");
        secondRowOpString = getIntent.getStringExtra("secondRowOp");
        firstRowSecondOpString = getIntent.getStringExtra("firstRowSecondOp");
        secondRowSecondOpString = getIntent.getStringExtra("secondRowSecondOp");
        firstRowAnsString = getIntent.getStringExtra("firstRowAns");
        secondRowAnsString = getIntent.getStringExtra("secondRowAns");
        firstRowUserString = getIntent.getStringExtra("firstRowUser");
        secondRowUserString = getIntent.getStringExtra("secondRowUser");
        firstRowAttack = getIntent.getIntExtra("firstRowAttack", 0);
        secondRowAttack = getIntent.getIntExtra("secondRowAttack", 0);
        isDeadOnFirstRoll = getIntent.getBooleanExtra("isDeadOnFirstRoll", false);
        isDead = getIntent.getBooleanExtra("isDead", false);
        opAttack = getIntent.getDoubleExtra("opAttack", 1);
        generateResults();
    }

    /**
     * Displays the results of the data passed in with savedInstanceState Bundle.
     */
    public void generateResults() {
        //update all text views.
        TextView firstRowFirstValue = (TextView) findViewById(R.id.firstRowFirstValue);
        TextView firstRowSecondValue = (TextView) findViewById(R.id.firstRowSecondValue);
        TextView secondRowFirstValue = (TextView) findViewById(R.id.secondRowFirstValue);
        TextView secondRowSecondValue = (TextView) findViewById(R.id.secondRowSecondValue);
        TextView firstRowOp = (TextView) findViewById(R.id.firstRowOp);
        TextView firstRowSecondOp = (TextView) findViewById(R.id.firstRowSecondOp);
        TextView firstRowAns = (TextView) findViewById(R.id.firstRowAns);
        TextView firstRowResults = (TextView) findViewById(R.id.firstRowResults);
        TextView secondRowOp = (TextView) findViewById(R.id.secondRowOp);
        TextView secondRowSecondOp = (TextView) findViewById(R.id.secondRowSecondOp);
        TextView secondRowAns = (TextView) findViewById(R.id.secondRowAns);
        TextView secondRowResults = (TextView) findViewById(R.id.secondRowResults);
        TextView isDefeated = (TextView) findViewById(R.id.defeatedTextView);
        if (isDead == true) {
            isDefeated.setText("Opponent Defeated");
        }
        if (isDeadOnFirstRoll == false) {
            firstRowFirstValue.setText(Integer.toString(firstRowFirstDice));
            firstRowSecondValue.setText(Integer.toString(firstRowSecondDice));
            secondRowFirstValue.setText(Integer.toString(secondRowFirstDice));
            secondRowSecondValue.setText(Integer.toString(secondRowSecondDice));
            firstRowOp.setText(firstRowOpString);
            firstRowSecondOp.setText(firstRowSecondOpString);
            firstRowAns.setText(firstRowAnsString);
            if(firstRowUserString.equals("Player")){
                firstRowResults.setText(firstRowUserString + " takes " + Integer.toString((int)(firstRowAttack * opAttack)) + " Damage");
            }
            else {
                firstRowResults.setText(firstRowUserString + " takes " + Integer.toString((int) (firstRowAttack * player.getDamageRate())) + " Damage");
            }
            secondRowOp.setText(secondRowOpString);
            secondRowSecondOp.setText(secondRowSecondOpString);
            secondRowAns.setText(secondRowAnsString);
            if(secondRowUserString.equals("Player")){
                secondRowResults.setText(secondRowUserString + " takes " + Integer.toString((int)(secondRowAttack * opAttack)) + " Damage");
            }
            else
            {
                secondRowResults.setText(secondRowUserString + " takes " + Integer.toString((int)(secondRowAttack * player.getDamageRate())) + " Damage");
            }

        } else {
            firstRowFirstValue.setText(Integer.toString(firstRowFirstDice));
            firstRowSecondValue.setText(Integer.toString(firstRowSecondDice));
            firstRowOp.setText(firstRowOpString);
            firstRowSecondOp.setText(firstRowSecondOpString);
            firstRowAns.setText(firstRowAnsString);
            if(firstRowUserString.equals("Player"))
            firstRowResults.setText(firstRowUserString + " takes " + Integer.toString((int)(firstRowAttack * opAttack)) + " Damage");
            else
                firstRowResults.setText(firstRowUserString + " takes " + Integer.toString((int) (firstRowAttack * player.getDamageRate())) + " Damage");
        }
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
        previous.putExtra("opList", this.opList);
        previous.putExtra("opponents", this.opponents);
        previous.putExtra("level", this.level);

        setResult(130, previous);
        this.finish();
    }

    /**
     * Overrides parent's onPause method. Saves the game when the app loses focus.
     */
    @Override
    public void onPause() {
        super.onPause();
        ObjectOutput out = null;
        String fileName = "savedGame";
        File saved = new File(getFilesDir(), fileName);

        try {
            out = new ObjectOutputStream(new FileOutputStream(saved, false));
            out.writeObject(player);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides parent's onStop method. Saves the game when the app is shut down.
     */
    @Override
    public void onStop() {
        super.onStop();
        ObjectOutput out = null;
        String fileName = "savedGame";
        File saved = new File(getFilesDir(), fileName);

        try {
            out = new ObjectOutputStream(new FileOutputStream(saved, false));
            out.writeObject(player);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
