package com.mats.niru;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;


class Game extends View{
    final Handler handler = new Handler();
    private ArrayList<MyImageButton> myImageButtonsArray = new ArrayList<>();
    private Runnable myRunnable;
    private Context context;
    private int round = 0;
    private int color;
    private int buttonsDown;
    private int correctClicks;
    private int highScore = 0;
    private boolean hardMode;
    private boolean buttonsOn = false;
    private ArrayList<Integer> playerArray = new ArrayList<>();
    private ArrayList<Integer> boardArray = new ArrayList<>();
    private int randomColor;
    private TextView textView;
    private TextView infoView;
    public Activity activity;
    private MyImageShow myImageShow;
    private int maxButtons;
    private String mode;
    private boolean handlerIsRunning;
    private int delayedRemove;


    public Game(Context context,Activity activity, boolean hardModeIn) {
        super(context);
        this.context = context;
        this.activity = activity;
        hardMode = hardModeIn;
        AlphaBackground alphaBackground;


        if (hardMode) {
            randomColor = 6;
            MyImageButton btnRed = (MyImageButton) this.activity.findViewById(R.id.myImageButton1);
            MyImageButton btnYellow = (MyImageButton) this.activity.findViewById(R.id.myImageButton2);
            MyImageButton btnBlue = (MyImageButton) this.activity.findViewById(R.id.myImageButton4);
            myImageShow = (MyImageShow) this.activity.findViewById(R.id.myImageButton7);
            btnRed.initButtons(this, R.drawable.redon,R.drawable.redoff, 1);
            btnYellow.initButtons(this,R.drawable.yellowon,R.drawable.yellowoff, 2);
            btnBlue.initButtons(this,R.drawable.blueon,R.drawable.blueoff, 4);
            myImageShow.addDrawable(0,R.drawable.buttonoff);
            myImageShow.addDrawable(1,R.drawable.redon);
            myImageShow.addDrawable(2,R.drawable.yellowon);
            myImageShow.addDrawable(3,R.drawable.orangeon);
            myImageShow.addDrawable(4,R.drawable.blueon);
            myImageShow.addDrawable(5,R.drawable.purpleon);
            myImageShow.addDrawable(6,R.drawable.greenon);
            maxButtons = 2;
            mode = "Hard";
        }

        else {
            randomColor = 4;
            MyImageButton btnRed = (MyImageButton) this.activity.findViewById(R.id.myImageButton1);
            MyImageButton btnYellow = (MyImageButton) this.activity.findViewById(R.id.myImageButton2);
            MyImageButton btnBlue = (MyImageButton) this.activity.findViewById(R.id.myImageButton4);
            MyImageButton btnGreen = (MyImageButton) this.activity.findViewById(R.id.myImageButton6);
            btnRed.initButtons(this, R.drawable.redon,R.drawable.redoff, 1);
            btnYellow.initButtons(this,R.drawable.yellowon,R.drawable.yellowoff, 2);
            btnBlue.initButtons(this,R.drawable.blueon,R.drawable.blueoff, 3);
            btnGreen.initButtons(this,R.drawable.greenon,R.drawable.greenoff, 4);
            myImageButtonsArray.add(0,btnRed);
            myImageButtonsArray.add(1,btnRed);
            myImageButtonsArray.add(2,btnYellow);
            myImageButtonsArray.add(3,btnBlue);
            myImageButtonsArray.add(4,btnGreen);
            maxButtons = 1;
            mode = "Normal";

        }
        textView = (TextView) this.activity.findViewById(R.id.textView);
        infoView = (TextView) this.activity.findViewById(R.id.textView2);
        alphaBackground = (AlphaBackground) (this.activity.findViewById(R.id.opacity));
        alphaBackground.init((TextView) this.activity.findViewById(R.id.counterView), this);
        loadData();
        alphaBackground.startAnimation(400,3);
    }

    public boolean isHandlerIsRunning() {
        return handlerIsRunning;
    }

    public void setHandlerIsRunning(boolean handlerIsRunning) {
        this.handlerIsRunning = handlerIsRunning;
    }

    public boolean isButtonsOn() {
        return buttonsOn;
    }

    public void setColor(int x){
        if (hardMode){
            color = color + x;
            myImageShow.setColor(color);
        }

    }

    public int getMaxButtons() {
        return maxButtons;
    }

    public void setButtonsDown(int buttonsDown) {
        this.buttonsDown = this.buttonsDown + buttonsDown;
    }
    public int getButtonsDown() {
        return buttonsDown;
    }

    private void saveData(int x) {
        highScore = Math.max(x, highScore );
        SharedPreferences sp =
                context.getSharedPreferences("MyPrefs",
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(mode, highScore);
        editor.commit();
    }
    private void loadData() {
        SharedPreferences sp = context.getSharedPreferences("MyPrefs",
                Context.MODE_PRIVATE);
        highScore = sp.getInt(mode, highScore);
    }
    public void makePlayerClick(int colorIn){
        if (hardMode){
            playerArray.add(color);
            color = 0;
            myImageShow.setColor(0);
        }
        else {
            playerArray.add(colorIn);
        }
    }

    public void setDelayedRemove(final int x){
        delayedRemove = x;
        myRunnable = new Runnable() {
            public void run() {
                color = color - x;
                myImageShow.setColor(color);
                setHandlerIsRunning(false);
            }
        };
        handler.postDelayed(myRunnable,200);
    }

    public void cancelDelayedRemove(){
        handler.removeCallbacks(myRunnable);
    }
    public void removePrevious(){
        color = color - delayedRemove;

    }

    public void startBoard() {
        Random random = new Random();
        color = random.nextInt(randomColor) + 1;
        boardArray.add(color);
        buttonsOn = false;
        infoView.setText(R.string.niru_says);

        boolean boardRunning = false;
        int y = 0;
        for (int a = 0; a< boardArray.size() ;a++) {

            final int i = a;
            if (a == 0 && boardArray.size() == i + 1){
                y = 100;
                boardRunning = true;
            }
            else if (a == 0){
                y = 800;
            }
            else if (boardArray.size() == i + 1){
                y = y + 800;
                boardRunning = true;
            }
            else {
                y = y + 800;
            }
            final int x = y;

            handler.postDelayed(new Runnable() {
                public void run() {
                    if (hardMode){
                        myImageShow.setColor(boardArray.get(i));
                    }
                    else{
                        myImageButtonsArray.get(boardArray.get(i)).colorOn();
                    }                }
            },x);
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (hardMode){
                        myImageShow.setColor(0);
                    }
                    else{
                        myImageButtonsArray.get(boardArray.get(i)).colorOff();
                    }

                }
            },x + 400 );

            if (boardRunning){
                handler.postDelayed(new Runnable() {
                    public void run() {
                        buttonsOn = true;
                        correctClicks = 0;
                        color = 0;
                        infoView.setText(R.string.your_turn);
                    }
                },x + 400 );
            }
        }
    }


    public void checkClick(){

        int checkIfLast = boardArray.size() - 1;

        if (playerArray.get(round).equals(boardArray.get(round))  && checkIfLast == round){
            round = 0;
            playerArray.clear();
            correctClicks++;
            saveData(correctClicks);

            textView.setText(String.valueOf(correctClicks));
            startBoard();
        }
        else if(playerArray.get(round).equals(boardArray.get(round))) {
            round++;
            correctClicks++;
            saveData(correctClicks);

        }
        else {
            round = 0;
            correctClicks = 0;
            playerArray.clear();
            boardArray.clear();
            textView.setText("0");
            alertDialogActivity();
        }
    }

    public void alertDialogActivity(){
        infoView.setText("");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("WRONG!");
        alertDialog.setMessage(" Do you want to try again?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                startBoard();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        alertDialog.show();
    }
}
