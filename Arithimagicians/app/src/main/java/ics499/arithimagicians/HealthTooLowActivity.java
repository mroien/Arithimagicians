package ics499.arithimagicians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Method to stop a user from fighting when their health is lower than 0
 */
public class HealthTooLowActivity extends Activity {
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_too_low);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");
        if(getIntent.getBooleanExtra("dice", false) == true){
            TextView textView = (TextView) findViewById(R.id.tooLowText);
            textView.setText("You did not select all 4 die! Make sure you choose all 4 dice");
        }

    }

    /**
     * Method to handle the click from a user
     * @param view
     */
    public void closeClick(View view) {
        Intent previous = new Intent(getApplicationContext(), DisplayMap.class);
        previous.putExtra("player", this.player);
        if(getIntent().getBooleanExtra("dice", false) == true){
            setResult(200, previous);
            this.finish();
        }
     else
        setResult(100, previous);
        this.finish();
    }

}
