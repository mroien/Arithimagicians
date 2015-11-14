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

public class ConnectAccount extends AppCompatActivity {
    private Player player;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_account);
        Intent getIntent = getIntent();
        player = (Player) getIntent.getSerializableExtra("player");

    }

    public void okClick(View view) {
        EditText edit = (EditText) findViewById(R.id.phoneNumber);
        phoneNumber = edit.getText().toString();
        checkNumberRest(phoneNumber);

    }

    public void closeClick(View view){
        Intent mapIntent = new Intent(ConnectAccount.this, DisplayMap.class);
        mapIntent.putExtra("player", player);
        startActivity(mapIntent);
    }


    public void checkNumberRest(String number) {
        String url = "http://192.168.29.115:8080/checkPhoneNumber?number=" + number;
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("Already Used")){
                            player.setUserId(Integer.parseInt(response));
                            Intent mapIntent = new Intent(ConnectAccount.this, DisplayMap.class);
                            mapIntent.putExtra("player", player);
                            startActivity(mapIntent);
                        }
                        else
                        {
                            TextView text = (TextView) findViewById(R.id.textView2);
                            text.setText("Number already used");
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
}
