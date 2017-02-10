package com.mahao.customhorizontialview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mahao.customhorizontialview.R;

import java.util.ArrayList;
import java.util.List;

public class CustomHorizontialView extends HorizontalScrollView {


    //每个界面显示个数
    private int showCount = 4;

    //每个child显示的宽度
    private int childWidth = 1;
    private LinearLayout mLinearLayout;
    private int mSubMiddle;
    private int mLeftMargin;
    private int mRightMargin;


    public CustomHorizontialView(Context context) {
        this(context,null);
    }

    public CustomHorizontialView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomHorizontialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //给horizontial添加view
        initData(context);
    }

    public  void initData(Context context) {

        //添加星期-
        List<String> list = new ArrayList<>();
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        list.add("星期六");
        list.add("星期天");
        list.add("星期一");

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));//LinearLayout.LayoutParams.WRAP_CONTENT
        mLinearLayout.setHorizontalGravity(LinearLayout.HORIZONTAL);

        for (int i = 0; i < 11; i++) {
            TextView textView = new TextView(context);
            textView.setText(list.get(i));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(childWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,0,10,0);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.txt_bg);
            textView.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
            textView.setTag(i);

            mLinearLayout.addView(textView);
        }
        if( getChildCount() < 1 ){

            addView(mLinearLayout);
        }
        //获取布局的宽
        int width = getWidth();


        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getSize(point);
        width = point.x;
        int height = point.y;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int widht = getWidth();
        int height = getHeight();

        //Log.i("mahao112",widht+"....ss..." + height);

        int measureWidth = getMeasuredWidth();
        int measureheight = getMeasuredHeight();

      //  Log.i("mahao112",measureWidth + "...." + measureheight);

        //获取每个子条目显示的宽度
        TextView childAt = (TextView) mLinearLayout.getChildAt(0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) childAt.getLayoutParams();
        mLeftMargin = params.leftMargin;
        mRightMargin = params.rightMargin;
        childWidth = (measureWidth -(( mLeftMargin + mRightMargin)*showCount)) / showCount;
        //设置每个子条目的宽度
        int count = mLinearLayout.getChildCount();
        for(int i = 0; i < count ; i++){

            TextView txtView = (TextView) mLinearLayout.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) txtView.getLayoutParams();
            layoutParams.width = childWidth;
            txtView.setLayoutParams(layoutParams);
        }

  /*      int measuredWidth = mLinearLayout.getMeasuredWidth();
        Log.i("mahao",measuredWidth+"wwwwwwwwwwwwwwwww");*/
        //是第三个子条目居中
        int middlePosi = measureWidth / 2;

        TextView txtView = (TextView) mLinearLayout.getChildAt(2);
        int left = txtView.getLeft();

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) txtView.getLayoutParams();

        //计算需要滚动的距离
        int  finalPosi = childWidth/2 + left;
        mSubMiddle = finalPosi - middlePosi;


    }


    //调用该方法设置显示点三个
    int count = 1;
    public void setCurrentShow(){

        if(count == 1){

            scrollTo(mSubMiddle,0);
            count++;
        }
    }

    //设置所有的选中的状态---背景变成红色
    private void setcurrentItem(boolean flag) {

    }

    /**
     *   监听滑动事件 ------滑动到下一个按钮，或者是上一个按钮
     * @param ev
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        //获取水平滚动的距离
        int scrollX = getScrollX();
        Log.i("mahao",scrollX+"...1...");

        switch (action){

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:

       /*         int newScrollX = getScrollX();
                int subScrollX = newScrollX - scrollX;

                Log.i("mahaoo",subScrollX+"..............");*/

                //判断每次滚动的距离---从中间跳转到下一个

                int scrollCount = scrollX / (childWidth + mLeftMargin + mRightMargin);
                Log.i("mahao222",scrollCount+"||||||");
                if(scrollCount > 0){

                   // scrollBy(childWidth + mLeftMargin + mRightMargin,0);
                }
                break;

            case MotionEvent.ACTION_UP:



                break;
        }
        return super.onTouchEvent(ev);
    }

}



















