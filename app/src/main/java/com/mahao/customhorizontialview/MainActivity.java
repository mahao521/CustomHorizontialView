package com.mahao.customhorizontialview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.mahao.customhorizontialview.widget.CustomHorizontialView;
import com.mahao.customhorizontialview.widget.LoopHorizontalScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        final CustomHorizontialView customView = (CustomHorizontialView) findViewById(R.id.my_custom_scroller_view);
        customView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                customView.setCurrentShow();
            }

        });
    }
}
