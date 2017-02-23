package com.mats.niru;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;


public class MyImageShow extends ImageView {

    private ArrayList<Integer> colors = new ArrayList<>();

    public void addDrawable(int index, int drawable){
        colors.add(index,drawable);
    }

    public MyImageShow(Context context) {
        super(context);
    }

    public MyImageShow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int colorIn){
        setImageResource(colors.get(colorIn));
    }
}
