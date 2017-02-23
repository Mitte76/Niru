package com.mats.niru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartScreen extends AppCompatActivity {
    int normalHigh;
    int hardHigh;
    TextView normal;
    TextView hard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        loadData();
        normal = (TextView) findViewById(R.id.highNormal);
        hard = (TextView) findViewById(R.id.highHard);
        normal.setText(String.format(getString(R.string.normal), normalHigh));
        hard.setText(String.format(getString(R.string.hard), hardHigh));





    }
    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        normalHigh = sp.getInt("Normal", normalHigh);
        hardHigh = sp.getInt("Hard", hardHigh);
    }
    public void goNormal(View view){
        Intent intent = new Intent(this, Niru.class);
        intent.putExtra("hardMode", false);
        startActivity(intent);
    }
    public void goHard(View view){
        Intent intent = new Intent(this, Niru.class);
        intent.putExtra("hardMode", true);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
//        String text = String.format(getString(R.string.normal), normalHigh);
//        String test = getString(R.string.normal);
        normal.setText(String.format(getString(R.string.normal), normalHigh));
        hard.setText(String.format(getString(R.string.hard), hardHigh));

    }
}
