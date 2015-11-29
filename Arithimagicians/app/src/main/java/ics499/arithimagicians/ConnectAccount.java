package ics499.arithimagicians;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Main class for connecting an android user with web application user
 */
public class ConnectAccount extends AppCompatActivity {
    // Player object of the player
    private Player player;
    // Phone number to connect
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_account);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");

    }

    /**
     * When the user clicks ok, call the rest service to connect the account
     *
     * @param view view of the application
     */
    public void okClick(View view) {
        EditText edit = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber = edit.getText().toString();
        checkNumberRest(phoneNumber);

    }

    /**
     * Close click if the user hasnt made an account or doesent want to connect, send the player to the map screen
     *
     * @param view view of the application
     */
    public void closeClick(View view) {
        Intent mapIntent = new Intent(ConnectAccount.this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }

    /**
     * Method to connect a phone number. First check to see if the number has been used, if it hasnt been used add it to the player object
     *
     * @param number
     */
    public void checkNumberRest(String number) {
        String url = "http://52.32.43.132:8080/checkPhoneNumber?number=" + number;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("Already Used")) {
                            player.setUserId(Integer.parseInt(response));
                            Intent mapIntent = new Intent(ConnectAccount.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);
                        } else {
                            TextView text = (TextView) findViewById(R.id.textView2);
                            text.setText("Number already used");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}
