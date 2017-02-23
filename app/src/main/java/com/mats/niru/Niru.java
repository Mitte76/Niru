package com.mats.niru;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

public class Niru extends AppCompatActivity {
    Game game;
    boolean hardMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        hardMode = getIntent().getExtras().getBoolean("hardMode");

        if (hardMode) {
            setContentView(R.layout.activity_niru_hard);
            game = new Game(this,this,hardMode);
        } else {
            setContentView(R.layout.activity_niru_normal);
            game = new Game(this,this,hardMode);
        }
        super.onCreate(savedInstanceState);
    }
}
