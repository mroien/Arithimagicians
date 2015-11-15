package ics499.arithimagicians;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 2owls on 11/14/2015.
 */
public class BuyDiceDialog extends DialogFragment {

    public static BuyDiceDialog newInstance(Player player, String die, int bonusCost) {
        Bundle args = new Bundle();
        BuyDiceDialog dia = new BuyDiceDialog();
        args.putSerializable("player", player);
        args.putString("die", die);
        args.putInt("cost", bonusCost);
        dia.setArguments(args);
        return dia;
    }

    public static BuyDiceDialog newInstance(int bonusCost) {
        Bundle args = new Bundle();
        BuyDiceDialog dia = new BuyDiceDialog();
        args.putInt("cost", bonusCost);
        dia.setArguments(args);
        return dia;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Player player = (Player) getArguments().getSerializable("player");
        final String die = getArguments().getString("die");
        final int cost = getArguments().getInt("cost");
        final int bonus = getArguments().getInt("bonus");
        final DialogFragment df = (DialogFragment) getTargetFragment();
        AlertDialog.Builder bonusBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_spend_confirm, null);
        TextView title = (TextView) view.findViewById(R.id.spendTitle);
        if (player != null) {
            title.setText("Buy extra " + die + " for " + cost + " XP?");
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
                                        player.addDie(new D4());
                                        break;
                                    case "d6":
                                        player.addDie(new D6());
                                        break;
                                    case "d8":
                                        player.addDie(new D8());
                                        break;
                                    case "d10":
                                        player.addDie(new D10());
                                        break;
                                    case "d12":
                                        player.addDie(new D12());
                                        break;
                                    case "d20":
                                        player.addDie(new D20());
                                        break;
                                }
                                player.spendXP(cost);
                            }
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
