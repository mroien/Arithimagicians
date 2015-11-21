package ics499.arithimagicians;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Brian Shaffer on 11/19/2015.
 * Activity for reading the help file.
 */
public class HelpActivity extends AppCompatActivity {

    /**
     * Overrides AppCompatActivity's onCreate method.
     * Takes a Bundle and calls super's onCreate method.
     * Sets the content view to the activity_help layout.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

}
