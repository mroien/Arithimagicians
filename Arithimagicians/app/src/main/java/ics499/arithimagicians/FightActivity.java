package ics499.arithimagicians;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        if(opList == null){
            opList = new ArrayList<String>();
        }
        if(opponents == null){
            opponents = new ArrayList<Opponent>();
        }
        setContentView(R.layout.activity_fight);
        setContent();
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
        diceUsed = player.getDiceUsed();
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        opProgressBar = (ProgressBar) findViewById(R.id.oppProgressBar);
        boolean generateOps = false;
        TextView firstRowFirstOp = (TextView) findViewById(R.id.firstRowFirstOp);
        TextView secondRowFirstOp = (TextView) findViewById(R.id.secondRowFirstOp);
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
            if(firstRow.indexOf(d) == 0) {
                firstRowTotal += d.rollDice();
            }
            else
            {
                if(firstRowFirstOp.getText().equals("+")){
                    firstRowTotal += d.rollDice();
                }
                else if(firstRowFirstOp.getText().equals("-")){
                    firstRowTotal = firstRowTotal - d.rollDice();
                }
                else if(firstRowFirstOp.getText().equals("*")){
                    firstRowTotal = firstRowTotal * d.rollDice();
                }
                else{
                    firstRowTotal = firstRowTotal / d.rollDice();
                }
            }
        }
        // For every dice in the second row list, have them roll dice and add it to the total
        for (Die d : secondRow) {
            if(secondRow.indexOf(d) == 0) {
                secondRowTotal += d.rollDice();
            }
            else
            {
                if(secondRowFirstOp.getText().equals("+")){
                    secondRowTotal += d.rollDice();
                }
                else if(secondRowFirstOp.getText().equals("-")){
                    secondRowTotal = secondRowTotal - d.rollDice();
                }
                else if(secondRowFirstOp.getText().equals("*")){
                    secondRowTotal = secondRowTotal * d.rollDice();
                }
                else{
                    secondRowTotal = secondRowTotal / d.rollDice();
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
        // Now check damage calulations
        // If the first row total from the dice is greater or equal to the answer
        if (firstRowTotal >= Integer.parseInt(firstRowAns.getText().toString())) {
            // do attack,
            int attack = firstRowTotal - Integer.parseInt(firstRowAns.getText().toString());
            currOpponet.takeDamage(attack);
            // recalc health,
            int gj2 = currOpponet.getCurrentHealth();
            opHealth.setText("Opponent Health: " + currOpponet.getCurrentHealth());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());

            // Check op health if dead rewards screen,
            if (currOpponet.getCurrentHealth() <= 0) {
                player.swapDiceBackToInv();
                opponents.remove(0);
                generateOps = true;
                generateNextOpponent();
            }
            // Else if it is less than the answer
        } else {
            // do enemy attack,
            int attack = Integer.parseInt(firstRowAns.getText().toString()) - firstRowTotal;
            player.takeDamage(attack);
            // recalc health,
            playerHealth.setText("Player Health: " + player.getCurrentHealth());
            int d = player.getPercentHealthLeft();
            playerProgressBar.setProgress(player.getPercentHealthLeft());

        }
        // IF the second row total from the dice is greater or equal to the answer
        if ((secondRowTotal >= Integer.parseInt(secondRowAns.getText().toString())) && !(currOpponet.getCurrentHealth() < 0)) {
            // do attack,
            int attack = secondRowTotal - Integer.parseInt(secondRowAns.getText().toString());
            currOpponet.takeDamage(attack);
            // recalc health,
            int o1 = currOpponet.getCurrentHealth();
            opHealth.setText("Opponent Health: " + currOpponet.getCurrentHealth());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());

            // Check op health if dead rewards screen,
            if (currOpponet.getCurrentHealth() <= 0) {
                player.swapDiceBackToInv();
               if(opponents.size() > 0)
                   opponents.remove(0);
                generateOps = true;
                generateNextOpponent();
            }
            // Else if it is less than the answer
        } else if(!(currOpponet.getCurrentHealth() < 0)){
            // do enemy attack,
            int attack = Integer.parseInt(secondRowAns.getText().toString()) - secondRowTotal;
            player.takeDamage(attack);
            // recalc health,
            playerHealth.setText("Player Health: " + player.getCurrentHealth());
            playerProgressBar.setProgress(player.getPercentHealthLeft());

        }
        // If at this point we have not gotten pushed to the rewards screen, lets see if the player is dead
        if (player.getCurrentHealth() <= 0) {
            player.swapDiceBackToInv();
            generateDeathScreen();
        }
        // Else the player is alive and so is the monster, so reset the dice locations to null
        // Generate new answers
        // Generate new operations
        // Swap the dice from the players usedDice array to the inventory and keep fighting
        else if(generateOps == false) {
            // reset operations and dice
            resetDicePics();
            generateAns(level);
            generateOperations(currOpponet.getOp());
            player.swapDiceBackToInv();
        }

    }

    private void generateDeathScreen() {
        Intent deathIntent = new Intent(this, DeathActivity.class);
        deathIntent.putExtra("player", player);
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
        startActivity(rewardsIntent);
    }

    private boolean checkFrozen(){
        Random r = new Random();
        int value = r.nextInt(100);
        if(value > 35){
            return true;
        }
        return false;
    }

    public void inventoryClicked(View view) {
        Intent invIntent = new Intent(this, InventoryActivity.class);
        invIntent.putExtra("player", player);
        startActivityForResult(invIntent, 100);

    }


    public void setContent() {
        String lastMap = player.getLastMap();
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.fightLayout);
        playerProgressBar = (ProgressBar) findViewById(R.id.playerProgressBar);
        opProgressBar = (ProgressBar) findViewById(R.id.oppProgressBar);
        playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
        opHealth = (TextView) findViewById(R.id.oppTextView);
        switch (level) {
            case "1_1":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidspearman));
                playerHealth = (TextView) findViewById(R.id.playerHealthTextView);
                playerHealth.setText("Player HP : " + player.getCurrentHealth());
                playerProgressBar.setProgress(player.getPercentHealthLeft());
                opHealth = (TextView) findViewById(R.id.oppTextView);
                opHealth.setText("Opponent HP: 3");
                opList.clear();
                opList.add("+");
                Opponent one = new Opponent(3, 0, "Gobloid Spearman", "zone1gobloidspearman");
                currOpponet = one;
                one.setOp(opList);
                opponents.add(one);
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_2":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidslasher));
                one = new Opponent(4, 1, "Gobloid slasher", "zone1gobloidslasher");
                currOpponet = one;
                playerHealth.setText("Player HP : " + player.getCurrentHealth());
                playerProgressBar.setProgress(player.getPercentHealthLeft());
                opHealth.setText("Opponent HP: 4");
                opList.clear();
                opList.add("+");
                one.setOp(opList);
                opponents.add(one);
                generateOperations(opList);
                generateAns(level);
                break;
            case "1_3":
                rl.setBackground(ContextCompat.getDrawable(this, R.drawable.zone1gobloidspearman));
                playerHealth.setText("Player HP : " + player.getCurrentHealth());
                playerProgressBar.setProgress(player.getPercentHealthLeft());
                one = new Opponent(4, 1, "Gobloid slasher", "zone1gobloidslasher");
                Opponent two = new Opponent(4, 1, "Gobloid Spearman", "zone1gobloidspearman");
                currOpponet = one;
                opHealth.setText("Opponent HP: " + currOpponet.getCurrentHealth());
                opList.clear();
                opList.add("+");
                opList.add("-");
                two.setOp(opList);
                one.setOp(opList);
                opponents.add(one);
                opponents.add(two);
                generateOperations(opList);
                generateAns(level);
                break;
        }
    }

    private void generateAns(String level) {
        TextView firstRowAns = (TextView) findViewById(R.id.firstRowFirstAns);
        TextView secondRowAns = (TextView) findViewById(R.id.secondRowAns);
        int ansMax = 0;
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
                ansMax = 12;
                break;

        }
        for (int i = 0; i < 2; i++) {
            Random r = new Random();
            int opIndex = r.nextInt(ansMax - 1) + 1;
            if (i == 0) {
                firstRowAns.setText(Integer.toString(opIndex));
            } else
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
                firstRowFirstOp.setText(opList.get(opIndex));
            } else
                secondRowFirstOp.setText(opList.get(opIndex));
        }

    }

    public void generateNextOpponent() {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.fightLayout);
        if (opponents.size() == 0) {
            rewardsScreen();
        } else {
            Opponent next = opponents.get(0);
            currOpponet = next;
            int resId = getResources().getIdentifier(next.getLayout(), "drawable", getPackageName());
            rl.setBackground(ContextCompat.getDrawable(this, resId));
            resetDicePics();
            generateAns(level);
            generateOperations(currOpponet.getOp());
            opProgressBar.setProgress(currOpponet.getPercentHealthLeft());
            opHealth.setText("Opponent Health : " + currOpponet.getCurrentHealth());
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
                if(frfd.getTag() != null)
                {
                    diceIntent.putExtra("diceFilled", frfd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "firstRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.firstRowSecondDice:
                ImageButton frsd = (ImageButton) findViewById(R.id.firstRowSecondDice);
                if(frsd.getTag() != null)
                {
                    diceIntent.putExtra("diceFilled", frsd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "firstRowSecondDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowFirstDice:
                ImageButton srfd = (ImageButton) findViewById(R.id.secondRowFirstDice);
                if(srfd.getTag() != null)
                {
                    diceIntent.putExtra("diceFilled", srfd.getTag().toString());
                    diceIntent.putExtra("alreadyFilled", true);
                }
                diceIntent.putExtra("diceLoc", "secondRowFirstDice");
                startActivityForResult(diceIntent, 110);
                break;
            case R.id.secondRowSecondDice:
                ImageButton srsd = (ImageButton) findViewById(R.id.secondRowSecondDice);
                if(srsd.getTag() != null)
                {
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
        if (resultCode == 104) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d4);
            img.setTag("d4");
        }
        else if (resultCode == 106) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d6);
            img.setTag("d6");
        }
        else if (resultCode == 108) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d8);
            img.setTag("d8");
        }
        else if (resultCode == 110) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d10);
            img.setTag("d10");
        }
        else if (resultCode == 112) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d12);
            img.setTag("d12");
        }
        else if (resultCode == 120) {
            this.player = (Player) data.getSerializableExtra("player");
            String dice = data.getStringExtra("diceSelected");
            String diceLoc = data.getStringExtra("diceLoc");
            currentElement = data.getStringExtra("element");
            ImageButton img = (ImageButton) this.findViewById(this.getResources().getIdentifier(diceLoc, "id", this.getPackageName()));
            img.setBackgroundResource(R.drawable.d20);
            img.setTag("d20");
        }
    }

}
