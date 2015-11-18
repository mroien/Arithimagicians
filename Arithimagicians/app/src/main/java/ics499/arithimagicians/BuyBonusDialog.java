package ics499.arithimagicians;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Brian Shaffer on 11/14/2015.
 */
public class BuyBonusDialog extends DialogFragment {

    /**
     * Called when player has enough experience to make the purchase. Creates an instance of the dialog
     * for buyiing a bonus to dice and puts the arguments in a Bundle to be passed between dialogs.
     * @param player
     * @param die
     * @param bonusCost
     * @param currBonus
     * @return
     */
    public static BuyBonusDialog newInstance(Player player, String die, int bonusCost, int currBonus) {
        Bundle args = new Bundle();
        BuyBonusDialog dia = new BuyBonusDialog();
        args.putSerializable("player", player);
        args.putString("die", die);
        args.putInt("cost", bonusCost);
        args.putInt("bonus", currBonus);
        dia.setArguments(args);
        return dia;
    }

    /**
     * Called when player does not have enough experience for the purchase. Only takes a bonus cost
     * for parsing the message back to the player.
     * @param bonusCost
     * @return
     */
    public static BuyBonusDialog newInstance(int bonusCost) {
        Bundle args = new Bundle();
        BuyBonusDialog dia = new BuyBonusDialog();
        args.putInt("cost", bonusCost);
        dia.setArguments(args);
        return dia;
    }

    /**
     * Overrides Dialog's onCreateDialog method.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //sets final arguments for using in listeners
        final Player player = (Player) getArguments().getSerializable("player");
        final String die = getArguments().getString("die");
        final int cost = getArguments().getInt("cost");
        final int bonus = getArguments().getInt("bonus");
        final DialogFragment df = (DialogFragment) getTargetFragment();
        AlertDialog.Builder bonusBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_spend_confirm, null);
        TextView title = (TextView) view.findViewById(R.id.spendTitle);

        //A player was passed, so confirm the player wants to make the purchase and update if so.
        if (player != null) {
            title.setText("Increase damage to " + (bonus + 1) + " on all " + die + " rolls for " + cost + " XP?");
            bonusBuilder.setCustomTitle(title)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (player.checkXP(cost)) {
                                switch (die) {
                                    case "d4":
                                        D4.incrementBonus();
                                        break;
                                    case "d6":
                                        D6.incrementBonus();
                                        break;
                                    case "d8":
                                        D8.incrementBonus();
                                        break;
                                    case "d10":
                                        D10.incrementBonus();
                                        break;
                                    case "d12":
                                        D12.incrementBonus();
                                        break;
                                    case "d20":
                                        D20.incrementBonus();
                                        break;
                                }
                                player.spendXP(cost);
                            }

                            //removes the old LevelChoiceDialog and creates a new one with the updated information
                            Fragment newFragment = LevelChoiceDialog.newInstance(player, die);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.remove(df);
                            transaction.add(newFragment,"buy");
                            transaction.addToBackStack(null);
                            // Commit the transaction
                            transaction.commit();
                        }
                    });
            return bonusBuilder.create();
        } else {

            //no player object was passed, so the player doesn't have enough xp.
            title.setText("Sorry, you do not have " + cost + " XP.");
            bonusBuilder.setCustomTitle(title)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            return bonusBuilder.create();
        }
    }
}
