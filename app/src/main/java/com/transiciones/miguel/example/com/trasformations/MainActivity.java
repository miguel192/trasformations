package com.transiciones.miguel.example.com.trasformations;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ViewGroup view;
    private ImageView circle;
    private boolean sizeChanged;
    private int savedWidth;
    private boolean positionChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();
        Button change_size = (Button) findViewById(R.id.change_size);
        change_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLayout();
            }
        });

        Button change_position = (Button) findViewById(R.id.change_position);
        change_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePosition();
            }
        });

        Button change_color = (Button) findViewById(R.id.change_color);
        change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();

            }
        });
    }

    private void setupLayout() {
        circle = (ImageView) findViewById(R.id.circle);
        view = (ViewGroup) findViewById(R.id.root_view);
    }

    private void changeLayout() {
        TransitionManager.beginDelayedTransition(view);

        ViewGroup.LayoutParams params = circle.getLayoutParams();
        if (sizeChanged) {
            params.width = savedWidth;
        } else {
            savedWidth = params.width;
            params.width = 200;
        }
        sizeChanged = !sizeChanged;
        circle.setLayoutParams(params);
    }

    private void changePosition() {
        TransitionManager.beginDelayedTransition(view);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) circle.getLayoutParams();
        Log.d("DEV", String.valueOf(lp));
        if (positionChanged) {
            lp.gravity = Gravity.CENTER;
        } else {
            lp.gravity = Gravity.LEFT;
        }
        positionChanged = !positionChanged;
        circle.setLayoutParams(lp);
    }

    private void changeColor() {

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ImageView circleColor = (ImageView) view.findViewById(R.id.circle);
        Drawable background = circleColor.getBackground();
        int colorFrom = Color.TRANSPARENT;
        if (background instanceof ColorDrawable) {
            colorFrom = ((ColorDrawable) background).getColor();
        }
        int colorTo = color;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(350); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ImageView circleColor = (ImageView) view.findViewById(R.id.circle);
                circleColor.setColorFilter((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
