package com.mats.niru;

import android.content.Context;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AlphaBackground extends LinearLayout{
    final Handler handler = new Handler();
    private Runnable myRunnable;
    private TextView myTextView;
    private Game game;
    private TextView tv;
    private int count;
    private int backgroundFadeDuration;
    private long previousTouchTime = 0;
    private long DOUBLE_CLICK_INTERVAL = ViewConfiguration.getDoubleTapTimeout();


    AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);

    public AlphaBackground(Context context) {
        super(context);
    }

    public AlphaBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init (TextView textView, Game gameIn) {
        myTextView = textView;
        game = gameIn;
    }

    public void startAnimation(final int backgroundFadeDurationIn, final int countIn){
        backgroundFadeDuration = backgroundFadeDurationIn;
        setVisibility(VISIBLE);
        animate().alpha(1).setDuration(backgroundFadeDuration);
        myRunnable = new Runnable() {
            public void run() {
                countDown(myTextView,countIn,backgroundFadeDuration);
            }
        };
        handler.postDelayed(myRunnable,backgroundFadeDuration + 100);
    }

    private void countDown(final TextView tev, final int countIn, final int backgroundFadeDuration) {
        tv = tev;
        count = countIn;
        if (count == 0) {
            tv.setText("");
                endAnimation(backgroundFadeDuration);
            return;
        }

        setAlpha(1);
        tv.setText(String.valueOf(count));
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation anim) {
                countDown(tv, count - 1,backgroundFadeDuration);
            }
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv.startAnimation(animation);
    }

    private void endAnimation(int backgroundFadeDuration){
        animate().alpha(0).setDuration(backgroundFadeDuration);
        myRunnable = new Runnable() {
            public void run() {
                setVisibility(GONE);
                game.startBoard();
            }
        };
        handler.postDelayed(myRunnable,backgroundFadeDuration + 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    long thisTouchTime = System.currentTimeMillis();
                    if (thisTouchTime - previousTouchTime <= DOUBLE_CLICK_INTERVAL) {
                    count = 1;
                    tv.clearAnimation();
                    }
                    previousTouchTime = thisTouchTime;
                    break;
            }
        return true;
    }
}
