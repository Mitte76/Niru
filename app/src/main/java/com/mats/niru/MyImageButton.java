package com.mats.niru;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class MyImageButton extends ImageButton {

    private Rect rect;
    private Game game;
    private boolean outside = false;
    private int colorOn;
    private int colorOff;
    private int id;
    private boolean down = false;


    public MyImageButton(Context context) {
        super(context);
    }

    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initButtons(Game gameIn, int colorOnIn, int colorOffIn, int idIn) {
        id = idIn;
        colorOn = colorOnIn;
        colorOff = colorOffIn;
        game = gameIn;
    }

    public void colorOn() {
        setImageResource(colorOn);
    }

    public void colorOff() {
        setImageResource(colorOff);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!(game.getButtonsDown() == game.getMaxButtons() && event.getAction()== MotionEvent.ACTION_DOWN) && game.isButtonsOn())  {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    rect = new Rect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
                    if (game.isHandlerIsRunning()){
                        game.cancelDelayedRemove();
                        game.removePrevious();
                        game.setColor(id);
                        game.setHandlerIsRunning(false);
                    }
                    else {
                        game.setColor(id);
                    }
                    setImageResource(colorOn);
                    down = true;
                    game.setButtonsDown(1);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!rect.contains(this.getLeft() + (int) event.getX(), this.getTop() + (int) event.getY()) && !outside && down) {
                        outside = true;
                        setImageResource(colorOff);
                        game.setColor(-id);
                    } else if (rect.contains(this.getLeft() + (int) event.getX(), this.getTop() + (int) event.getY()) && outside && down) {
                        setImageResource(colorOn);
                        game.setColor(id);
                        outside = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    outside = false;
                    if (down) {
                        if (!rect.contains(this.getLeft() + (int) event.getX(), this.getTop() + (int) event.getY())) {
                            game.setButtonsDown(-1);
                        } else {
                            game.setButtonsDown(-1);
                            setImageResource(colorOff);

                            if (game.getButtonsDown() >= 1) {
                                game.setDelayedRemove(id);
                                game.setHandlerIsRunning(true);
                            }
                            else {
                                game.setHandlerIsRunning(false);
                                game.makePlayerClick(id);
                                game.cancelDelayedRemove();
                                game.checkClick();
                            }
                        }
                    }
                    down = false;
                    break;
            }
        }
        return true;
    }
}

