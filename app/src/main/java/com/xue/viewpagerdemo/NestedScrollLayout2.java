package com.xue.viewpagerdemo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;




/**
 * Created by 薛贤俊 on 2019/2/22.
 */
public class NestedScrollLayout2 extends FrameLayout implements NestedScrollingParent2 {

    /**
     * tablayout和viewpager组成的子item
     */
    private View mChildView;
    /**
     * 最外层的RecyclerView
     */
    private RecyclerView mRootList;
    /**
     * 内部子RecyclerView
     */
    private RecyclerView mChildList;

    /**
     * tablayout+viewpager的高度，应该是屏幕上剩余可滑动的高度.
     */
    private int pagerHeight;


    /**
     * 在tab+vp的item bindview时，用这个高度赋值给item的高度
     * @return
     */
    public int getTabAndVpHeight(){
        return pagerHeight;
    }


    public void measurePagerHeightAtFirstTime(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = getMeasuredHeight();
                pagerHeight = height;
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    private int mAxes;

    public NestedScrollLayout2(@NonNull Context context) {
        super(context);
    }

    public NestedScrollLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * tablayout和viewpager所组成的item attatch window和detach win时调用
     * @param mChildView
     */
    public void setChildView(View mChildView) {
        this.mChildView = mChildView;
    }

    /**
     * 切换viewpager里的recycleview时： 当前fragment切换到前台时调用
     * @param mChildList
     */
    public void setChildList(RecyclerView mChildList) {
        this.mChildList = mChildList;
    }

    /**
     * 初始化时调用，设置外部主recycleview
     * @param recyclerView
     */
    public void setRootList(RecyclerView recyclerView) {
        mRootList = recyclerView;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }



     @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        mAxes = axes;
    }


    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        mAxes = SCROLL_AXIS_NONE;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }




    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (mChildView == null) {
            return;
        }
        if (target == mRootList) {
            onParentScrolling(mChildView.getTop(), dy, consumed);
        } else {
            onChildScrolling(mChildView.getTop(), dy, consumed);
        }
    }



    /**
     * 父列表在滑动
     *
     * @param childTop
     * @param dy
     * @param consumed
     */
    private void onParentScrolling(int childTop, int dy, int[] consumed) {
        //列表已经置顶
        if (childTop == 0) {
            if (dy > 0 && mChildList != null) {
                //还在向下滑动，此时滑动子列表
                mChildList.scrollBy(0, dy);
                consumed[1] = dy;
            } else {
                if (mChildList != null && mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mChildList.scrollBy(0, dy);
                }
            }
        } else {
            if (childTop < dy) {
                consumed[1] = dy - childTop;
            }
        }
    }

    private void onChildScrolling(int childTop, int dy, int[] consumed) {
        if (childTop == 0) {
            if (dy < 0) {
                //向上滑动
                if (!mChildList.canScrollVertically(dy)) {
                    consumed[1] = dy;
                    mRootList.scrollBy(0, dy);
                }
            }
        } else {
            if (dy < 0 || childTop > dy) {
                consumed[1] = dy;
                mRootList.scrollBy(0, dy);
            } else {
                //dy大于0
                consumed[1] = dy;
                mRootList.scrollBy(0, childTop);
            }
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return mAxes;
    }

}
