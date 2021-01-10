package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListDetail extends AppCompatActivity {
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        textView1 =(TextView)findViewById(R.id.textitem1);
        String Tempholder =getIntent().getStringExtra("Listviewclickvalue");
        textView1.setText(Tempholder);

    }
}
