package ics499.arithimagicians;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Dice activity class that pops up when a user selects to use a dice in a fight activity
 */
public class DiceLevelUpActivity extends Activity {
    private Player player;
    private ArrayList<Die> dice;
    private String diceLoc;
    private ArrayList<Die> diceUsed;
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
        previous.putExtra("opList", opList);
        previous.putExtra("opponents", opponents);
        FragmentTransaction ft;
        LevelChoiceDialog newFragment;
        switch (view.getId()) {
            case R.id.d4:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d4");
                    newFragment.show(ft, "test");
                break;
            case R.id.d6:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d6");
                    newFragment.show(ft, "test");
                break;
            case R.id.d8:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d8");
                    newFragment.show(ft, "test");
                break;
            case R.id.d10:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d10");
                    newFragment.show(ft, "test");
                break;
            case R.id.d12:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d12");
                    newFragment.show(ft, "test");
                break;
            case R.id.d20:
                    ft = getFragmentManager().beginTransaction();
                    // Create and show the dialog.
                    newFragment = LevelChoiceDialog.newInstance(player, "d20");
                    newFragment.show(ft, "test");

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
        t = (TextView) findViewById(R.id.d10TextView);
        t.setText(Integer.toString(d10));
        t = (TextView) findViewById(R.id.d12TextView);
        t.setText(Integer.toString(d12));
        t = (TextView) findViewById(R.id.d20TextView);
        t.setText(Integer.toString(d20));

    }

    public static class LevelChoiceDialog extends DialogFragment {

        public static LevelChoiceDialog newInstance(Player player, String die) {
            Bundle args = new Bundle();
            LevelChoiceDialog dia = new LevelChoiceDialog();
            args.putSerializable("player", player);
            args.putString("die", die);
            dia.setArguments(args);
            return dia;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_dice_level_up, null);
            TextView titleView = (TextView) view.findViewById(R.id.lvlTile);
            String die = getArguments().getString("die");
            Player player = (Player) getArguments().getSerializable("player");
            String title = "Level up " + die + ".\nYou have " + player.getXp() + "XP.";
            titleView.setText(title);
            int count = 0;
            int bonus = 0;
            for (Die d : player.getDice()){
                if (die.equals(d.getDiceType())){
                    count++;
                }
            }
            String countText = "" + count;
            TextView countView = (TextView) view.findViewById(R.id.buyCount);
            bonus = getBonus(die);
            countView.setText(countText);
            TextView bonusView = (TextView) view.findViewById(R.id.bonusCount);
            bonusView.setText("" + bonus);
            builder.setView(view);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        public int getBonus(String type){
            int bonus = 0;
            switch (type){
                case "d4":
                    bonus = D4.getBonus();
                    break;
                case "d6":
                    bonus = D6.getBonus();
                    break;
                case "d8":
                    bonus = D8.getBonus();
                    break;
                case "d10":
                    bonus = D10.getBonus();
                    break;
                case "d12":
                    bonus = D12.getBonus();
                    break;
                case "d20":
                    bonus = D20.getBonus();
                    break;
            }
            return bonus;
        }
    }
}



