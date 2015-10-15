package ics499.arithimagicians;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog.*;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity  implements Serializable{
    private boolean splash = false;
    private String account;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newGameClicked(View view) {
        // if no account found, ask if they want to make an account and display the webpage
        if (account == null) {
            Builder builder = new Builder(this);
            builder
                    .setTitle("No Account Found")
                    .setMessage("Would you like to make an account?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Yes button clicked, do something

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Create user account without any information besides random accountId, and set level to the start
                            player = new Player();
                            // Send to start screen:
                            Intent mapIntent = new Intent(MainActivity.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);


                        }
                    }).show();
        }
    }
}
