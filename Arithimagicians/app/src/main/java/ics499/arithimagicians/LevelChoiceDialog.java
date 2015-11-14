package ics499.arithimagicians;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 2owls on 11/14/2015.
 */
public class LevelChoiceDialog extends DialogFragment {

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
        View view = inflater.inflate(R.layout.dialog_dice_level_up, null);
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
        TextView diceCostView = (TextView) view.findViewById((R.id.buyCost));
        int diceCost = getDiceCost(player, die);
        diceCostView.setText("" + diceCost);
        TextView bonusCostView = (TextView) view.findViewById((R.id.bonusCost));
        int bonusCost = getBonusCost(player, die);
        bonusCostView.setText("" + bonusCost);
        Button bonusBtn = (Button) view.findViewById(R.id.buyBonus);
        bonusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = (Player) getArguments().getSerializable("player");
                String die = getArguments().getString("die");
                int passBonusCost = getBonusCost(player, die);
                int currBonus = getBonus(die);
                if (player.checkXP(passBonusCost)) {
                    // Create and show the dialog.
                    BuyBonusDialog bonusFragment = BuyBonusDialog.newInstance(player, die, passBonusCost, currBonus);
                    bonusFragment.show(getFragmentManager().beginTransaction(), "test");
                } else {
                    BuyBonusDialog bonusFragment = BuyBonusDialog.newInstance(passBonusCost);
                    bonusFragment.show(getFragmentManager().beginTransaction(), "test");
                }
            }
        });
        Button diceBtn = (Button) view.findViewById(R.id.buyDice);
        diceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = (Player) getArguments().getSerializable("player");
                String die = getArguments().getString("die");
                int passDieCost = getDiceCost(player, die);
                if (player.checkXP(passDieCost)) {
                    // Create and show the dialog.
                    BuyDiceDialog diceFragment = BuyDiceDialog.newInstance(player, die, passDieCost);
                    diceFragment.show(getFragmentManager().beginTransaction(), "test");
                } else {
                    BuyBonusDialog bonusFragment = BuyBonusDialog.newInstance(passDieCost);
                    bonusFragment.show(getFragmentManager().beginTransaction(), "test");
                }
            }
            });
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private int getBonus(String type){
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

    private int getDiceCost(Player player, String type){
        int cost = 0;
        for (Die die : player.getDice()) {
            if (die.getDiceType().equals(type)) {
                cost = die.getDieCost();
                return cost;
            }
        }
        if (cost == 0){
            cost = Integer.parseInt(type.substring(1));
        }
        return cost;
    }
    private int getBonusCost(Player player, String type){
        int cost = 0;
        switch (type){
            case "d4":
                for (Die die : player.getDice()){
                    if (die instanceof D4){
                        cost = ((D4) die).getBonusCost();
                        return cost;
                    }
                }
                break;
            case "d6":
                for (Die die : player.getDice()){
                    if (die instanceof D6){
                        cost = ((D6) die).getBonusCost();
                        return cost;
                    }
                }
                break;
            case "d8":
                for (Die die : player.getDice()){
                    if (die instanceof D8){
                        cost = ((D8) die).getBonusCost();
                        return cost;
                    }
                }
                break;
            case "d10":
                for (Die die : player.getDice()){
                    if (die instanceof D10){
                        cost = ((D10) die).getBonusCost();
                        return cost;
                    }
                }
                break;
            case "d12":
                for (Die die : player.getDice()){
                    if (die instanceof D12){
                        cost = ((D12) die).getBonusCost();
                        return cost;
                    }
                }
                break;
            case "d20":
                for (Die die : player.getDice()){
                    if (die instanceof D20){
                        cost = ((D20) die).getBonusCost();
                        return cost;
                    }
                }
                break;
        }
        if (cost == 0){
            cost = Integer.parseInt(type.substring(1));
        }
        return cost;
    }
}
