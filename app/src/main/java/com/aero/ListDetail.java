package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListDetail extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        textView1 = (TextView) findViewById(R.id.textitem1);
        textView2 = (TextView) findViewById(R.id.textitem2);
        textView3 = (TextView) findViewById(R.id.textitem4);
        textView4 = (TextView) findViewById(R.id.textitem5);
        final HashMap<String, String> Tempholder = (HashMap<String, String>) getIntent().getSerializableExtra("key");
        assert Tempholder != null;
        textView1.setText(Tempholder.get("flightId"));
        textView2.setText(Tempholder.get("dairportCode"));
        textView3.setText(Tempholder.get("flightNumber"));
        textView4.setText(Tempholder.get("airportCode"));

        button = (Button) findViewById(R.id.bookflight);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =new Intent(ListDetail.this,BookingActivity.class);
                intent.putExtra("key",Tempholder);
                startActivity(intent);
            }

        });
    }
}
