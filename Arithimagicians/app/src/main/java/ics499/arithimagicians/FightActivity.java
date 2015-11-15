package ics499.arithimagicians;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

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
    private ArrayList<Opponent> opponents;
    private Opponent currOpponet;
    private String currentElement;
    private TextView playerHealth;
    private TextView opHealth;
    private ProgressBar opProgressBar;
    private ProgressBar playerProgressBar;
    private ArrayList<Die> diceUsed;
    private ArrayList<String> opList;
    private boolean generateOps;
    private boolean displayedResults;
    private int totalEnemyHP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        level = getIntent.getStringExtra("level");
        opponents = new ArrayList<Opponent>();
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        opProgressBar = (ProgressBar) findViewById(R.id.oppProgressBar);
        playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
        opHealth = (TextView) findViewById(R.id.oppTextView);
        opList = getIntent.getStringArrayListExtra("opList");
        opponents = (ArrayList<Opponent>) getIntent.getSerializableExtra("opponents");
        displayedResults = false;
        if (opList == null) {
            opList = new ArrayList<String>();
        }
        if (opponents == null) {
            opponents = new ArrayList<Opponent>();
        }
        setContentView(R.layout.activity_fight);
        setContent();
        totalEnemyHP = 0;
        for (Opponent enemy : opponents) {
            totalEnemyHP += enemy.getTotalHealth();
        }
    }

    public void attackClicked(View view) {
        currentElement = "Light";
        // Generate all of the stuff we need to calculate damage
        // Create the list of the dice in the first row
        ArrayList<Die> firstRow = new ArrayList<Die>();
        // Create the list of dice in the second row
        ArrayList<Die> secondRow = new ArrayList<Die>();
        // Find which dice was used in the first row first position
        ImageButton firstRowFirstDice = (ImageButton) findViewById(R.id.firstRowFirstDice);
        // Get the value of the dice as a string
        String frfd = firstRowFirstDice.getTag().toString();
        // Find which dice was used in the first row second position
        ImageButton firstRowSecondDice = (ImageButton) findViewById(R.id.firstRowSecondDice);
        // Get the value of the dice as a string
        String frsd = firstRowSecondDice.getTag().toString();
        // Find which dice was used in the second row first slot
        ImageButton secondRowFirstDice = (ImageButton) findViewById(R.id.secondRowFirstDice);
        // Get the value of the dice as as tring
        String srfd = secondRowFirstDice.getTag().toString();
        // Find which dice was used in the second row second slot
        ImageButton secondRowSecondDice = (ImageButton) findViewById(R.id.secondRowSecondDice);
        // Get the value of the dice as a string
        String srsd = secondRowSecondDice.getTag().toString();
        // Find the text view for the first answer
        TextView firstRowAns = (TextView) findViewById(R.id.firstRowFirstAns);
        // Find the text view for the second answer
        TextView secondRowAns = (TextView) findViewById(R.id.secondRowAns);
        TextView firstRowSecondOp = (TextView) findViewById(R.id.firstRowSecondOp);
        TextView secondRowSecondOp = (TextView) findViewById(R.id.secondRowSecondOp);
        boolean isDead = false;
        diceUsed = player.getDiceUsed();
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        opProgressBar = (ProgressBar) findViewById(R.id.oppProgressBar);
        generateOps = false;
        TextView firstRowFirstOp = (TextView) findViewById(R.id.firstRowFirstOp);
        TextView secondRowFirstOp = (TextView) findViewById(R.id.secondRowFirstOp);
        int firstRowFirstDiceRoll = 0;
        int firstRowSecondDiceRoll = 0;
        int secondRowFirstDiceRoll = 0;
        int secondRowSecondDiceRoll = 0;


        // Loop through the dice used array list to figure out which dice was used in which row
        for (int i = 0; i < diceUsed.size(); i++) {
            // If the dice selected equals the string value of the first row first position dice and the first row array list is less than 2, add it to the first row list
            if (diceUsed.get(i).getDiceType().equals(frfd) && firstRow.size() < 2) {
                firstRow.add(diceUsed.get(i));
                // Else if the dice value is equal to the first row second position and the first row array list is less than 2, add it to the first row list
            } else if (diceUsed.get(i).getDiceType().equals(frsd) && firstRow.size() < 2) {
                firstRow.add(diceUsed.get(i));
                // Else if the dice value is equal to the second row first dice and that list is less than two, add it to the second row list
            } else if (diceUsed.get(i).getDiceType().equals(srfd) && secondRow.size() < 2) {
                secondRow.add(diceUsed.get(i));
                // Else if the dice value is equal to the second row second dice and that list is less than two, add it to the second row list
            } else if (diceUsed.get(i).getDiceType().equals(srsd) && secondRow.size() < 2) {
                secondRow.add(diceUsed.get(i));
            }

        }
        int firstRowTotal = 0;
        int secondRowTotal = 0;

        // For every dice in the first row list, have them roll dice and add it to the total
        for (Die d : firstRow) {
            if (firstRow.indexOf(d) == 0) {
                firstRowFirstDiceRoll = d.rollDice();
                firstRowTotal += firstRowFirstDiceRoll;
            } else {
                if (firstRowFirstOp.getText().equals("+")) {
                    firstRowSecondDiceRoll = d.rollDice();
                    firstRowTotal += firstRowSecondDiceRoll;
                } else if (firstRowFirstOp.getText().equals("-")) {
                    firstRowSecondDiceRoll = d.rollDice();
                    firstRowTotal = firstRowTotal - firstRowSecondDiceRoll;

                } else if (firstRowFirstOp.getText().equals("*")) {
                    firstRowSecondDiceRoll = d.rollDice();
                    firstRowTotal = firstRowTotal * firstRowSecondDiceRoll;
                } else {
                    firstRowSecondDiceRoll = d.rollDice();
                    firstRowTotal = firstRowTotal / firstRowSecondDiceRoll;

                }
            }
        }
        // For every dice in the second row list, have them roll dice and add it to the total
        for (Die d : secondRow) {
            if (secondRow.indexOf(d) == 0) {
                secondRowFirstDiceRoll = d.rollDice();
                secondRowTotal += secondRowFirstDiceRoll;
            } else {
                if (secondRowFirstOp.getText().equals("+")) {
                    secondRowSecondDiceRoll = d.rollDice();
                    secondRowTotal += secondRowSecondDiceRoll;
                } else if (secondRowFirstOp.getText().equals("-")) {
                    secondRowSecondDiceRoll = d.rollDice();
                    secondRowTotal = secondRowTotal - secondRowSecondDiceRoll;

                } else if (secondRowFirstOp.getText().equals("*")) {
                    secondRowSecondDiceRoll = d.rollDice();
                    secondRowTotal = secondRowTotal * secondRowSecondDiceRoll;
                } else {
                    secondRowSecondDiceRoll = d.rollDice();
                    secondRowTotal = secondRowTotal / secondRowSecondDiceRoll;

                }
            }
        }
        // If the selected element is fire, add + 1 to each row
        if (currentElement.equals("Fire")) {
            firstRowTotal++;
            secondRowTotal++;
        }
        Log.i("firstrowdm", "first row damage : " + firstRowTotal);
        Log.i("firstrowdmg", "second rwo damage : " + secondRowTotal);
        this.player.updateMaxSingleDamage(firstRowTotal);
        this.player.updateMaxSingleDamage(secondRowTotal);
        this.player.updateMaxTotalDamage(firstRowTotal + secondRowTotal);
        int firstRowAttack = 0;
        int secondRowAttack = 0;
        String firstRowUser = "";
        String secondRowUser = "";
        double totalRolls = 0;
        double totalHits = 0;
        boolean isDeadOnFirstRoll = false;
        // Now check damage calulations
        // If the first row total from the dice is greater or equal to the answer
        if (firstRowTotal >= Integer.parseInt(firstRowAns.getText().toString())) {
            // do attack,
            firstRowAttack = firstRowTotal - Integer.parseInt(firstRowAns.getText().toString());
            currOpponet.takeDamage((int) (firstRowAttack * player.getDamageRate()));
            firstRowUser = "Enemy";
            // recalc health,
            int gj2 = currOpponet.getCurrentHealth();
            opHealth.setText(currOpponet.getName() + " HP: " + currOpponet.getCurrentHealth());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());
            this.player.updateTotalHits();
            this.player.updateTotalRolls();
            totalRolls++;
            totalHits++;
            // Check op health if dead rewards screen,
            if (currOpponet.getCurrentHealth() <= 0) {
                player.swapDiceBackToInv();
                opponents.remove(0);
                generateOps = true;
                isDeadOnFirstRoll = true;
                isDead = true;
                this.player.updateHighestAcc(totalHits / totalRolls * 100);
                generateResults(firstRowFirstDiceRoll, firstRowSecondDiceRoll, secondRowFirstDiceRoll, secondRowSecondDiceRoll, firstRowFirstOp.getText().toString(), secondRowFirstOp.getText().toString(), firstRowSecondOp.getText().toString(), secondRowSecondOp.getText().toString(), firstRowAns.getText().toString(), secondRowAns.getText().toString(), firstRowAttack, firstRowUser, secondRowAttack, secondRowUser, isDeadOnFirstRoll, isDead);
                generateNextOpponent();
            }
            // Else if it is less than the answer
        } else {
            // do enemy attack,
            firstRowAttack = Integer.parseInt(firstRowAns.getText().toString()) - firstRowTotal;
            player.takeDamage(firstRowAttack + currOpponet.getAttack());
            firstRowUser = "Player";
            // recalc health,
            playerHealth.setText("Player Health: " + player.getCurrentHealth());
            int d = player.getPercentHealthLeft();
            playerProgressBar.setProgress(player.getPercentHealthLeft());
            this.player.updateTotalRolls();
            totalRolls++;
        }


        // IF the second row total from the dice is greater or equal to the answer
        if ((secondRowTotal >= Integer.parseInt(secondRowAns.getText().toString())) && !(currOpponet.getCurrentHealth() < 0) && !(isDead)) {
            // do attack,
            secondRowAttack = secondRowTotal - Integer.parseInt(secondRowAns.getText().toString());
            currOpponet.takeDamage((int) (secondRowAttack * player.getDamageRate()));
            secondRowUser = "Enemy";
            // recalc health,
            int o1 = currOpponet.getCurrentHealth();
            opHealth.setText(currOpponet.getName() + " HP: " + currOpponet.getCurrentHealth());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());
            this.player.updateTotalHits();
            this.player.updateTotalRolls();
            totalRolls++;
            totalHits++;
            // Check op health if dead rewards screen,
            if (currOpponet.getCurrentHealth() <= 0) {
                player.swapDiceBackToInv();
                if (opponents.size() > 0)
                    opponents.remove(0);
                generateOps = true;
                isDead = true;
                player.swapDiceBackToInv();
                generateResults(firstRowFirstDiceRoll, firstRowSecondDiceRoll, secondRowFirstDiceRoll, secondRowSecondDiceRoll, firstRowFirstOp.getText().toString(), secondRowFirstOp.getText().toString(), firstRowSecondOp.getText().toString(), secondRowSecondOp.getText().toString(), firstRowAns.getText().toString(), secondRowAns.getText().toString(), firstRowAttack, firstRowUser, secondRowAttack, secondRowUser, false, isDead);
                generateNextOpponent();
            }
            // Else if it is less than the answer
        } else if (!(currOpponet.getCurrentHealth() < 0) && !(isDead)) {
            // do enemy attack,
            secondRowAttack = Integer.parseInt(secondRowAns.getText().toString()) - secondRowTotal;
            player.takeDamage(secondRowAttack + currOpponet.getAttack());
            secondRowUser = "Player";
            // recalc health,
            playerHealth.setText("Player Health: " + player.getCurrentHealth());
            playerProgressBar.setProgress(player.getPercentHealthLeft());
            this.player.updateTotalRolls();
            totalRolls++;
        }


        // If at this point we have not gotten pushed to the rewards screen, lets see if the player is dead
        if (player.getCurrentHealth() <= 0) {
            player.swapDiceBackToInv();
            generateResults(firstRowFirstDiceRoll, firstRowSecondDiceRoll, secondRowFirstDiceRoll, secondRowSecondDiceRoll, firstRowFirstOp.getText().toString(), secondRowFirstOp.getText().toString(), firstRowSecondOp.getText().toString(), secondRowSecondOp.getText().toString(), firstRowAns.getText().toString(), secondRowAns.getText().toString(), firstRowAttack, firstRowUser, secondRowAttack, secondRowUser, false, false);
            generateDeathScreen();
        }
        // Else the player is alive and so is the monster, so reset the dice locations to null
        // Generate new answers
        // Generate new operations
        // Swap the dice from the players usedDice array to the inventory and keep fighting
        else if (generateOps == false) {
            // reset operations and dice
            player.swapDiceBackToInv();
            generateResults(firstRowFirstDiceRoll, firstRowSecondDiceRoll, secondRowFirstDiceRoll, secondRowSecondDiceRoll, firstRowFirstOp.getText().toString(), secondRowFirstOp.getText().toString(), firstRowSecondOp.getText().toString(), secondRowSecondOp.getText().toString(), firstRowAns.getText().toString(), secondRowAns.getText().toString(), firstRowAttack, firstRowUser, secondRowAttack, secondRowUser, false, false);
            resetDicePics();
            generateAns(level);
            generateOperations(currOpponet.getOp());

            ImageButton frfdTag = (ImageButton) findViewById(R.id.firstRowFirstDice);
            frfdTag.setTag(null);
            ImageButton frsdTag = (ImageButton) findViewById(R.id.firstRowSecondDice);
            frsdTag.setTag(null);
            ImageButton srfdTag = (ImageButton) findViewById(R.id.secondRowFirstDice);
            srfdTag.setTag(null);
            ImageButton srsdTag = (ImageButton) findViewById(R.id.secondRowSecondDice);
            srsdTag.setTag(null);

        }


    }

    private void generateResults(int firstRowFirstDice, int firstRowSecondDice, int secondRowFirstDice,
                                 int secondRowSecondDice, String firstRowOp, String secondRowOp,
                                 String firstRowSecondOp, String secondRowSecondOp, String firstRowAns,
                                 String secondRowAns, int firstRowAttack, String firstRowUser, int secondRowAttack,
                                 String secondRowUser, boolean isDeadOnFirstRoll, boolean isDead) {

        Intent results = new Intent(this, DiceRollResults.class);
        results.putExtra("player", player);
        results.putExtra("level", level);
        results.putExtra("opponents", opponents);
        results.putExtra("opList", opList);
        results.putExtra("firstRowFirstDice", firstRowFirstDice);
        results.putExtra("firstRowSecondDice", firstRowSecondDice);
        results.putExtra("secondRowFirstDice", secondRowFirstDice);
        results.putExtra("secondRowSecondDice", secondRowSecondDice);
        results.putExtra("firstRowOp", firstRowOp);
        results.putExtra("secondRowOp", secondRowOp);
        results.putExtra("firstRowSecondOp", firstRowSecondOp);
        results.putExtra("secondRowSecondOp", secondRowSecondOp);
        results.putExtra("firstRowAns", firstRowAns);
        results.putExtra("secondRowAns", secondRowAns);
        results.putExtra("firstRowAttack", firstRowAttack);
        results.putExtra("firstRowUser", firstRowUser);
        results.putExtra("secondRowAttack", secondRowAttack);
        results.putExtra("secondRowUser", secondRowUser);
        results.putExtra("isDeadOnFirstRoll", isDeadOnFirstRoll);
        results.putExtra("isDead", isDead);
        startActivityForResult(results, 100);
    }

    private void generateDeathScreen() {
        Intent deathIntent = new Intent(this, DeathActivity.class);
        deathIntent.putExtra("player", player);
        deathIntent.putExtra("XP", (int) totalEnemyHP / 2);
        startActivity(deathIntent);
    }

    private void resetDicePics() {
        ImageButton firstRowFirstDice = (ImageButton) findViewById(R.id.firstRowFirstDice);
        ImageButton firstRowSecondDice = (ImageButton) findViewById(R.id.firstRowSecondDice);
        ImageButton secondRowFirstDice = (ImageButton) findViewById(R.id.secondRowFirstDice);
        ImageButton secondRowSecondDice = (ImageButton) findViewById(R.id.secondRowSecondDice);

        firstRowFirstDice.setBackgroundResource(R.drawable.border);
        firstRowSecondDice.setBackgroundResource(R.drawable.border);
        secondRowFirstDice.setBackgroundResource(R.drawable.border);
        secondRowSecondDice.setBackgroundResource(R.drawable.border);
    }

    private void rewardsScreen() {
        Intent rewardsIntent = new Intent(this, RewardActivity.class);
        rewardsIntent.putExtra("player", player);
        rewardsIntent.putExtra("level", level);
        rewardsIntent.putExtra("XP", totalEnemyHP);
        startActivity(rewardsIntent);
    }


    public void inventoryClicked(View view) {
        Intent invIntent = new Intent(this, InventoryActivity.class);
        invIntent.putExtra("player", player);
        startActivityForResult(invIntent, 100);
    }


    public void setContent() {
        String lastMap = player.getLastMap();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.fightLayout);
        switch (level) {
            case "1_1":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidspearman));
                Opponent spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone1gobloidspearman");
                currOpponet = spearman;
                setProgressBars(currOpponet);
                spearman.setOp(opList);
                opponents.add(spearman);
                opList.clear();
                opList.add("+");
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_2":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidslasher));
                Opponent slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone1gobloidslasher");
                currOpponet = slasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                slasher.setOp(opList);
                opponents.add(slasher);
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_3":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidslasher));
                slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone1gobloidslasher");
                spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone1gobloidspearman");
                currOpponet = slasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                spearman.setOp(opList);
                slasher.setOp(opList);
                opponents.add(slasher);
                opponents.add(spearman);
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_4":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidslasher));
                slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone1gobloidslasher");
                Opponent slasherTwo = new Opponent(Opponent.Stats.GB_SLASHER, "zone1gobloidslasher");
                spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone1gobloidspearman");
                currOpponet = slasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                spearman.setOp(opList);
                slasher.setOp(opList);
                slasherTwo.setOp(opList);
                opponents.add(slasher);
                opponents.add(spearman);
                opponents.add(slasherTwo);
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_5":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidspearman));
                spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone1gobloidspearman");
                Opponent shaman = new Opponent(Opponent.Stats.GB_SHAMAN, "zone1gobloidshaman");
                Opponent grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone1skeletoniangrabber");
                currOpponet = spearman;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                spearman.setOp(opList);
                shaman.setOp(opList);
                grabber.setOp(opList);
                opponents.add(spearman);
                opponents.add(shaman);
                opponents.add(grabber);
                generateOperations(opList);
                generateAns(level);
                break;
            case "2_1":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone2gobloidslasher));
                slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone2gobloidslasher");
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone2skeletoniangrabber");
                currOpponet = slasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                slasher.setOp(opList);
                grabber.setOp(opList);
                opponents.add(slasher);
                opponents.add(grabber);
                generateOperations(opList);
                generateAns(level);
                break;
            case "2_2":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone2skeletoniangrabber));
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone2skeletoniangrabber");
                Opponent smasher = new Opponent(Opponent.Stats.SK_SMASHER, "zone2skeletoniansmasher");
                Opponent grabberTwo = new Opponent(Opponent.Stats.SK_GRABBER, "zone2skeletoniangrabber");
                currOpponet = grabber;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                grabber.setOp(opList);
                smasher.setOp(opList);
                grabberTwo.setOp(opList);
                opponents.add(grabber);
                opponents.add(smasher);
                opponents.add(grabberTwo);
                generateOperations(opList);
                generateAns(level);
                break;
            case "2_3":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone2skeletoniansmasher));
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone2skeletoniangrabber");
                smasher = new Opponent(Opponent.Stats.SK_SMASHER, "zone2skeletoniansmasher");
                Opponent smasherTwo = new Opponent(Opponent.Stats.SK_SMASHER, "zone2skeletoniansmasher");
                currOpponet = smasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                grabber.setOp(opList);
                smasher.setOp(opList);
                smasherTwo.setOp(opList);
                opponents.add(smasher);
                opponents.add(grabber);
                opponents.add(smasherTwo);
                generateOperations(opList);
                generateAns(level);
                break;
            case "2_4":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone2skeletonianwizard));
                Opponent wizard = new Opponent(Opponent.Stats.SK_WIZARD, "zone2skeletonianwizard");
                currOpponet = wizard;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                wizard.setOp(opList);
                opponents.add(wizard);
                generateOperations(opList);
                generateAns(level);
                break;
            case "2_5":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone2skeletoniangrabber));
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone2skeletoniangrabber");
                wizard = new Opponent(Opponent.Stats.SK_WIZARD, "zone2skeletonianwizard");
                currOpponet = grabber;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                wizard.setOp(opList);
                grabber.setOp(opList);
                opponents.add(grabber);
                opponents.add(wizard);
                generateOperations(opList);
                generateAns(level);
                break;
            case "3_1":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone3skeletoniangrabber));
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone3skeletoniangrabber");
                spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone3gobloidspearman");
                shaman = new Opponent(Opponent.Stats.GB_SHAMAN, "zone3gobloidshaman");
                currOpponet = grabber;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                spearman.setOp(opList);
                shaman.setOp(opList);
                grabber.setOp(opList);
                opponents.add(grabber);
                opponents.add(spearman);
                opponents.add(shaman);
                generateOperations(opList);
                generateAns(level);
                break;
            case "3_2":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone3skeletoniangrabber));
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone3skeletoniangrabber");
                smasher = new Opponent(Opponent.Stats.SK_SMASHER, "zone3skeletoniansmasher");
                slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone3gobloidslasher");
                currOpponet = grabber;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                opList.add("/");
                smasher.setOp(opList);
                slasher.setOp(opList);
                grabber.setOp(opList);
                opponents.add(grabber);
                opponents.add(smasher);
                opponents.add(slasher);
                generateOperations(opList);
                generateAns(level);
                break;
            case "3_3":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone3gobloidslasher));
                smasher = new Opponent(Opponent.Stats.SK_SMASHER, "zone3skeletoniansmasher");
                slasher = new Opponent(Opponent.Stats.GB_SLASHER, "zone3gobloidslasher");
                slasherTwo = new Opponent(Opponent.Stats.GB_SLASHER, "zone3gobloidslasher");
                currOpponet = slasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                opList.add("/");
                smasher.setOp(opList);
                slasher.setOp(opList);
                slasherTwo.setOp(opList);
                opponents.add(slasher);
                opponents.add(smasher);
                opponents.add(slasherTwo);
                generateOperations(opList);
                generateAns(level);
                break;
            case "3_4":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone3skeletoniansmasher));
                smasher = new Opponent(Opponent.Stats.SK_SMASHER, "zone3skeletoniansmasher");
                shaman = new Opponent(Opponent.Stats.GB_SLASHER, "zone3gobloidslasher");
                smasherTwo = new Opponent(Opponent.Stats.GB_SLASHER, "zone3skeletoniansmasher");
                currOpponet = smasher;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                opList.add("/");
                smasher.setOp(opList);
                shaman.setOp(opList);
                smasherTwo.setOp(opList);
                opponents.add(smasher);
                opponents.add(shaman);
                opponents.add(smasherTwo);
                generateOperations(opList);
                generateAns(level);
                break;
            case "3_5":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone3gobloidspearman));
                spearman = new Opponent(Opponent.Stats.GB_SPEARMAN, "zone3gobloidspearman");
                shaman = new Opponent(Opponent.Stats.GB_SHAMAN, "zone3gobloidshaman");
                grabber = new Opponent(Opponent.Stats.SK_GRABBER, "zone3skeletoniangrabber");
                wizard = new Opponent(Opponent.Stats.SK_WIZARD, "zone3skeletonianwizard");
                currOpponet = spearman;
                setProgressBars(currOpponet);
                opList.clear();
                opList.add("+");
                opList.add("-");
                opList.add("*");
                opList.add("/");
                spearman.setOp(opList);
                shaman.setOp(opList);
                grabber.setOp(opList);
                wizard.setOp(opList);
                opponents.add(spearman);
                opponents.add(grabber);
                opponents.add(shaman);
                opponents.add(wizard);
                generateOperations(opList);
                generateAns(level);
                break;
        }
    }

    private void setProgressBars(Opponent opponent) {
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        opProgressBar = (ProgressBar) findViewById(R.id.oppProgressBar);
        playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
        playerHealth.setText("Player HP : " + player.getCurrentHealth());
        playerProgressBar.setProgress(player.getPercentHealthLeft());
        opHealth = (TextView) findViewById(R.id.oppTextView);
        String oppBar = opponent.getName() + " HP: " + opponent.getTotalHealth();
        opHealth.setText(oppBar);
    }

    private void generateAns(String level) {
        TextView firstRowAns = (TextView) findViewById(R.id.firstRowFirstAns);
        TextView secondRowAns = (TextView) findViewById(R.id.secondRowAns);
        int ansMax = 0;
        int ansMult = 0;
        int ansDiv = 0;
        switch (level) {
            case "1_1":
                ansMax = 9;
                break;
            case "1_2":
                ansMax = 9;
                break;
            case "1_3":
                ansMax = 9;
                break;
            case "1_4":
                ansMax = 9;
                break;
            case "1_5":
                ansMax = 9;
                break;
            case "2_1":
                ansMax = 13;
                break;
            case "2_2":
                ansMax = 13;
                ansMult = 20;
                break;
            case "2_3":
                ansMax = 13;
                ansMult = 20;
                break;
            case "2_4":
                ansMax = 13;
                ansMult = 32;
                break;
            case "2_5":
                ansMax = 13;
                ansMult = 32;
                break;
            case "3_1":
                ansMax = 16;
                ansMult = 64;
                break;
            case "3_2":
                ansMax = 16;
                ansMult = 64;
                ansDiv = 4;
                break;
            case "3_3":
                ansMax = 16;
                ansMult = 80;
                ansDiv = 4;
                break;
            case "3_4":
                ansMax = 16;
                ansMult = 80;
                ansDiv = 4;
                break;
            case "3_5":
                ansMax = 16;
                ansMult = 80;
                ansDiv = 4;
                break;
        }

        for (int i = 0; i < 2; i++) {
            Random r = new Random();
            int opIndex;
            if (i == 0) {
                switch (firstRowOp) {
                    case "+":
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "-":
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "*":
                        opIndex = r.nextInt(ansMult - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "/":
                        opIndex = r.nextInt(ansDiv - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    default:
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                }
                firstRowAns.setText(Integer.toString(opIndex));
            } else
                switch (secondRowOp) {
                    case "+":
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "-":
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "*":
                        opIndex = r.nextInt(ansMult - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    case "/":
                        opIndex = r.nextInt(ansDiv - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                    default:
                        opIndex = r.nextInt(ansMax - 1);
                        if (opIndex < 3) {
                            opIndex = 3;
                        }
                        break;
                }
            secondRowAns.setText(Integer.toString(opIndex));
        }
    }

    private void generateOperations(ArrayList<String> opList) {
        TextView firstRowFirstOp = (TextView) findViewById(R.id.firstRowFirstOp);
        TextView secondRowFirstOp = (TextView) findViewById(R.id.secondRowFirstOp);

        for (int i = 0; i < 2; i++) {
            Random r = new Random();
            int opIndex = r.nextInt((opList.size() - 1) + 1);
            if (i == 0) {
                firstRowOp = opList.get(opIndex);
                firstRowFirstOp.setText(opList.get(opIndex));

            } else
                secondRowOp = opList.get(opIndex);
            secondRowFirstOp.setText(opList.get(opIndex));
        }
    }

    public void generateNextOpponent() {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.fightLayout);
        ImageButton frfd = (ImageButton) findViewById(R.id.firstRowFirstDice);
        frfd.setTag(null);
        ImageButton frsd = (ImageButton) findViewById(R.id.firstRowSecondDice);
        frsd.setTag(null);
        ImageButton srfd = (ImageButton) findViewById(R.id.secondRowFirstDice);
        srfd.setTag(null);
        ImageButton srsd = (ImageButton) findViewById(R.id.secondRowSecondDice);
        srsd.setTag(null);
        if (opponents.size() == 0) {
            updateLeaderboard();
            rewardsScreen();
        } else {
            updateLeaderboard();
            Opponent next = opponents.get(0);
            currOpponet = next;
            int resId = getResources().getIdentifier(next.getLayout(), "drawable", getPackageName());
            rl.setBackgroundColor(Color.WHITE);
            rl.setBackground(ContextCompat.getDrawable(this, resId));
            resetDicePics();
            generateAns(level);
            generateOperations(currOpponet.getOp());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());
            opHealth.setText(currOpponet.getName() + " HP: " + currOpponet.getCurrentHealth());
        }
    }

    public void diceClicked(View view) {
        Intent diceIntent = new Intent(this, DiceActivity.class);
        diceIntent.putExtra("player", player);
        diceIntent.putExtra("opponents", opponents);
        diceIntent.putExtra("opList", opList);

        switch (view.getId()) {
            case R.id.firstRowFirstDice:
                ImageButton frfd = (ImageButton) findViewById(R.id.firstRowFirstDice);
                if (frfd.getTag() != null) {
                    diceIntent.putExtra("diceFilled", frfd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "firstRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.firstRowSecondDice:
                ImageButton frsd = (ImageButton) findViewById(R.id.firstRowSecondDice);
                if (frsd.getTag() != null) {
                    diceIntent.putExtra("diceFilled", frsd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "firstRowSecondDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowFirstDice:
                ImageButton srfd = (ImageButton) findViewById(R.id.secondRowFirstDice);
                if (srfd.getTag() != null) {
                    diceIntent.putExtra("diceFilled", srfd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "secondRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowSecondDice:
                ImageButton srsd = (ImageButton) findViewById(R.id.secondRowSecondDice);
                if (srsd.getTag() != null) {
                    diceIntent.putExtra("diceFilled", srsd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "secondRowSecondDice");
                startActivityForResult(diceIntent, 110);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
            playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
            playerHealth.setText("Player HP : " + player.getCurrentHealth());
            playerProgressBar.setProgress(player.getPercentHealthLeft());
        } else if (resultCode == 104) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d4);
            img.setTag("d4");
        } else if (resultCode == 106) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d6);
            img.setTag("d6");
        } else if (resultCode == 108) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d8);
            img.setTag("d8");
        } else if (resultCode == 110) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d10);
            img.setTag("d10");
        } else if (resultCode == 112) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d12);
            img.setTag("d12");
        } else if (resultCode == 120) {
            this.player = (Player) data.getSerializableExtra("player");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d20);
            img.setTag("d20");
        } else if (resultCode == 130) {
            this.player = (Player) data.getSerializableExtra("player");
            this.opList = data.getStringArrayListExtra("opList");
            this.opponents = (ArrayList<Opponent>) data.getSerializableExtra("opponents");
            this.level = data.getStringExtra("level");
            displayedResults = true;

        }
    }

    public void updateLeaderboard() {
        String url = "http://192.168.29.115:8080/updateLeaderboard?accountId=" + this.player.getUserId() + "&level=" + this.player.getLastMap() + "&accuracyPerLevel=" + this.player.getTotalAcc()
                + "&highestAcc=" + this.player.getHighestAcc() + "&maxTotalDmg=" + this.player.getMaxTotalDamage() + "&maxSingleDmg=" + this.player.getMaxSingleDamage();
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("No Powerups")) {

                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

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
