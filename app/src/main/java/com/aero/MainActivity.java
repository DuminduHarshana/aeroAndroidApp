package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {
    EditText username_edit;
    EditText password_edit;
    String username;
    String password;
    String TAG = "Request";
    Button button;
    Button btn_login;
   private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_edit = findViewById(R.id.username);
        password_edit = findViewById(R.id.password);
        btn_login =findViewById(R.id.btn_login);
        baseUrl ="http://192.168.56.1:8080/api/v1/login";

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     username = username_edit.getText().toString();
                     password = password_edit.getText().toString();
                    if (username.isEmpty() || password.isEmpty()){
                        Toast.makeText(v.getContext(), "Please Fill Required ",Toast.LENGTH_SHORT).show();
                    }else{
                        try {

                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                baseUrl, null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(TAG, response.toString());
                                        if(Boolean.parseBoolean(response.toString())){
                                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() {

                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("username", username);
                                    params.put("password" , password);

                                    return params;
                                }

                            };

// Adding request to request queue
                            AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);



                        }
                        catch (Exception ex) {
                            String error = ex.getMessage();
                        }
                    }

                    }



            });
            button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this,FlightListActivity.class);
                startActivity(i);
            }
        });


    }

}
