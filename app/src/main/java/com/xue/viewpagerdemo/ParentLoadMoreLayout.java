package com.xue.viewpagerdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParentLoadMoreLayout extends LinearLayout  {

    int loadMoreHeight = 90;

    public ParentLoadMoreLayout(Context context) {
        super(context);
    }

    public ParentLoadMoreLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentLoadMoreLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadMoreView(TextView textView){

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParentLoadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*@Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if ((dy > 0 && getScrollY() < loadMoreHeight) ||
                (dy < 0 && getScrollY() > 0)) {
            consumed[1] = dy;
            scrollBy(dx, dy);
        }
    }*/
}
