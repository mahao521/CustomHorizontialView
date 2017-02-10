package com.mahao.customhorizontialview.widget;

/**
 * Created by Penghy on 2017/2/8.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Description：描述
 * 邮箱：wanglelong@yzhlbj.com
 * History：
 * 作者  日期         简要介绍相关操作
 * hsk-hsk on 2016/11/22 10:53  Create
 * hsk-hsk on 2016/11/22 10:53  添加，删除，修改某某方法
 */
public class LoopHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {

    public static final int SELECTED_COLOR = Color.parseColor("#335397");
    private List<String> menuItems = new ArrayList<>();
    private boolean isInit = false;
    // 滚动条的宽度
    private int hsv_width;
    // 总共有多少个view
    private int child_count;
    // 每一个view的宽度
    private int child_width;
    // 预计显示在屏幕上的view的个数
    private int child_show_count;
    // 一开始居中选中的view
    private int child_start;
    private List<TextView> titleList = new ArrayList<TextView>();
    private LinearLayout linearLayout;
    private int defaultSize = 18,selectedSize = 27;

    private OnItemClickListener onItemClickListener;

    public LoopHorizontalScrollView(Context context) {
        super(context);
    }

    public LoopHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {

        if(isInit){
            return;
        }

        isInit = true;

        defaultSize = 6;
        selectedSize = 8;


        hsv_width = getWidth();
        child_count = 7;
        child_show_count = 4;
        //start_mahao

        Calendar calendar =  Calendar.getInstance();
        String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        int currentWeek = 1;
        if(week.equals("1")){

            currentWeek = 7;

        }else if(week.equals("2")){

            currentWeek = 1;
        }else if(week.equals("3")){

            currentWeek = 2;
        }else if(week.equals("4")){

            currentWeek = 3;
        }else if(week.equals("5")){
            currentWeek = 4;
        }else if(week.equals("6")){
            currentWeek = 5;
        }else if(week.equals("7")){
            currentWeek = 6;
        }
        //end_mahao
        child_start = currentWeek;
        int child_width_temp = hsv_width / child_show_count;
        if (child_width_temp % 2 != 0) {
            child_width_temp++;
        }
        child_width = child_width_temp;

        menuItems.add("星期一");
        menuItems.add("星期二");
        menuItems.add("星期三");
        menuItems.add("星期四");
        menuItems.add("星期五");
        menuItems.add("星期六");
        menuItems.add("星期天");

        linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 100));//LinearLayout.LayoutParams.WRAP_CONTENT
        linearLayout.setHorizontalGravity(LinearLayout.HORIZONTAL);
        for (int i = 0; i < child_count; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new ViewGroup.LayoutParams(child_width,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setText(menuItems.get(i));
            textView.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
            textView.setOnClickListener(this);
            textView.setTag(i);
            textView.setTextSize(defaultSize);
            titleList.add(textView);
            linearLayout.addView(textView);
        }

        for (int i = 0; i < child_count; i++) {

            TextView textView = new TextView(context);
            textView.setLayoutParams(new ViewGroup.LayoutParams(child_width,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setText(menuItems.get(i));
            textView.setGravity(Gravity.BOTTOM|Gravity.CENTER_VERTICAL);
            textView.setOnClickListener(this);
            textView.setTag(7 + i);
            textView.setTextSize(defaultSize);
            titleList.add(textView);
            linearLayout.addView(textView);
        }

        addView(linearLayout);
        initHsvTouch();
        initStart();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 给滚动控件添加view，只有重复两个列表才能实现循环滚动
     */
    private void initHsvTouch() {

        setOnTouchListener(new OnTouchListener() {

            private int pre_item;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // TODO Auto-generated method stub
                boolean flag = false;
                int x = getScrollX();

                Log.i("mahao",x+".....................");
                int current_item = (x + hsv_width / 2) / child_width + 1;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        flag = false;
                        if (x <= child_width) {
                            scrollBy(
                                    child_width * child_count, 0);

                            Log.i("mahao",child_width*child_count+"///");
                            current_item += child_count;

                        } else if (x >= (child_width * child_count * 2 - hsv_width - child_width)) {

                            Log.i("mahao",-child_width * child_count+"******");
                            Log.i("mahao",child_width * child_count * 2 - hsv_width - child_width+"@@@@@@");
                            scrollBy(-child_width * child_count, 0);
                            current_item -= child_count;
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        flag = true;
                        smoothScrollTo(child_width
                                        * current_item - child_width / 2 - hsv_width / 2,
                                getScrollY());
                        //Log.e("AlbumActivity", "MotionEvent.ACTION_UP>>" + (current_item % child_count));
                        if(onItemClickListener != null){
                            onItemClickListener.onItemClick(current_item % child_count);
                        }
                        break;

                }

                if (pre_item == 0) {
                    isChecked(current_item, true);
                } else if (pre_item != current_item) {
                    isChecked(pre_item, false);
                    isChecked(current_item, true);
                }
                pre_item = current_item;
                return flag;
            }
        });
    }

    public static interface OnItemClickListener {

        public void onItemClick(int postion);
    }

    @Override
    public void onClick(View view) {

        int position = (int) view.getTag() + 1;
        //int color = Color.rgb(233, 63, 33); // #E94221
        for (int i = 0; i < titleList.size(); i++) {
            TextView textView = titleList.get(i);
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultSize);
        }

        final TextView textView = titleList.get(position - 1);
        textView.setTextColor(SELECTED_COLOR);
        textView.setTextSize(selectedSize);

        if (position == 14 || position == 13) {
            position -= 7;
        }

        Log.e("position", "position>>>>" + position);
        int child_start_item = position;
        if ((position * child_width - child_width / 2 - hsv_width / 2) <= child_width) {
            child_start_item += child_count;
        }
        smoothScrollTo(child_width * child_start_item
                        - child_width / 2 - hsv_width / 2,
                getScrollY());
        isChecked(child_start_item, true);
        if(onItemClickListener != null){

            onItemClickListener.onItemClick(position>=7?position-7:position);
        }
    }


    /**
     * 设置指定位置的状态
     *
     * @param item
     * @param isChecked
     */

    private void isChecked(int item, boolean isChecked) {

        TextView textView = (TextView) linearLayout.getChildAt(item - 1);
        if (isChecked) {
            textView.setTextColor(SELECTED_COLOR);
            textView.setTextSize(selectedSize);
        } else {

            textView.setTextColor(Color.GRAY);
            textView.setTextSize(defaultSize);
        }

    }


    /**
     * 刚开始进入界面时的初始选中项的处理
     */

    private void initStart() {

        final ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                observer.removeOnPreDrawListener(this);
                int child_start_item = child_start;
                if ((child_start * child_width - child_width / 2 - hsv_width / 2) <= child_width) {
                    child_start_item += child_count;
                }
                scrollTo(child_width * child_start_item
                                - child_width / 2 - hsv_width / 2,
                        getScrollY());
                isChecked(child_start_item, true);
                return false;
            }

        });
    }

}
