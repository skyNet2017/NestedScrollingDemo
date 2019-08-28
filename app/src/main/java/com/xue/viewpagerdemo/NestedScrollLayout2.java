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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;


/**
 * Created by 薛贤俊 on 2019/2/22.
 *
 * 启蒙文章： https://www.jianshu.com/p/bc6d703e7ca9
 * https://juejin.im/post/5d3e639e51882508dc163606
 *
 *
 *
 *
 *
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
                pagerHeight = height ;
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
    public void setChildList(RecyclerView mChildList, View tvLoadMore) {
        this.mChildList = mChildList;
        this.tvLoadMore = tvLoadMore;
    }

    View tvLoadMore;

    /**
     * 初始化时调用，设置外部主recycleview
     * @param recyclerView
     */
    public void setRootList(RecyclerView recyclerView) {
        mRootList = recyclerView;
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

    /**
     * 有嵌套滑动到来了，判断父控件是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父控件不一定是一级就能找到的，可能挑了两级父控件的父控件，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param axes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 父控件是否接受嵌套滑动， 只有接受了才会执行剩下的嵌套滑动方法
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        boolean isVertical =  axes == ViewCompat.SCROLL_AXIS_VERTICAL;
        if(isVertical){
            isBottom = false;
        }
        return isVertical;
    }


    /**
     * parent里的伪代码：
     * public boolean onTouchEvent(MotionEvent ev) {
     *   . . .
     *   final int actionMasked = . . .;
     *   . . .
     *   switch (actionMasked) {
     *     case MotionEvent.ACTION_DOWN: {
     *       . . .
     *       startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
     *       break;
     *     }
     *     case MotionEvent.ACTION_MOVE:
     *       . . .
     *       if (dispatchNestedPreScroll(...) {
     *         deltaY -= mScrollConsumed[1];
     *         . . .
     *       }
     *   . . .
     * }
     *
     * 当我们滚动nestedChild时，nestedChild进行实际的滚动前，会先调用nestParent的这个onNestedPreScroll方法。
     * nestedParent在这个方法中可以把子View想要滚动的距离消耗掉一部分或是全部消耗。
     *
     * dy代表了本次NestedparentView想要滑动的距离。若我们向上滑动，dy就是正的，向下就是负的。
     *
     * getScorllY()会返回ParentView的mScrollY参数，为正则表示当前ParentView的内容已经向上滚动了一段距离，否则表示向下滚动过一段距离
     * mScrollY的绝对值为View的上边框与View的内容的上边框的距离，当View向上滚动时，mScrollY是正的；当View向下滚动时，mScrollY是负的
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子控件想要变化的距离 dx<0 向右滑动 dx>0 向左滑动
     * @param dy       垂直方向嵌套滑动的子控件想要变化的距离 dy<0 向下滑动 dy>0 向上滑动
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子控件当前父控件消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子控件做出相应的调整
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //当tab和vp不在屏幕内时，就不用处理滑动了
        if (mChildView == null) {
            return;
        }
        if (target == mRootList) {
            onParentScrolling(mChildView.getTop(), dy, consumed);
        } else {
            onChildScrolling(mChildView.getTop(), dy, consumed);
        }
    }



    int footHeight = 180;
    int innerLvHeight;
    int innerPageHeight;
    /**
     * 父列表在滑动
     *
     * @param childTop
     * @param dy
     * @param consumed
     */
    private void onParentScrolling(int childTop, int dy, int[] consumed) {
        //tablayout+viewpager列表已经在parent里置顶
        if (childTop == 0) {
            if(!isTabsTop){
                isTabsTop = true;
                if(scrollListener != null){
                    scrollListener.onTabsStateChanged(isTabsTop);
                }
            }
            //Log.e("onNestedPreScroll","tablayout+viewpager列表已经在parent里置顶");
            if (dy > 0 && mChildList != null) {
                //Log.e("onNestedPreScroll","还在向上滑动，此时向上滑动子列表。");
                // todo 如果子列表没有显示，而显示的是状态view，那么需要将mChildList置空


                if(!mChildList.canScrollVertically(1)){
                    Log.e("onNestedPreScroll","子列表向上滑，连"+dy+"个像素都滑不动了，说明到底了");
                    /*if(tvLoadMore.getScrollY() < footHeight){
                        Log.e("onNestedPreScroll",dy+"上滑整体linearlayout："+tvLoadMore.getScrollY());
                        if(dy <= footHeight -tvLoadMore.getScrollY()){
                            tvLoadMore.scrollBy(0,dy);
                            consumed[1] = dy;
                        }else {
                            tvLoadMore.scrollBy(0,footHeight - tvLoadMore.getScrollY());
                            consumed[1] = footHeight - tvLoadMore.getScrollY();
                        }
                    }*/


                    if(!isBottom){
                        isBottom = true;
                        if(scrollListener != null){
                            scrollListener.onReachBottom(mChildList,mChildView);
                        }
                        tvLoadMore.findViewById(R.id.tv_loadmore).setVisibility(VISIBLE);
                        //reduceInnerRecycleviewHeight(dy,consumed);
                        //refreshLayout.autoLoadMore()
                    }

                }else {
                    mChildList.scrollBy(0, dy);
                    consumed[1] = dy;
                }

            } else {
                //dy<0 ,向下滑
                if(tvLoadMore.getScrollY() > 0){
                    /*Log.e("onNestedPreScroll",dy+"下滑整体linearlayout："+tvLoadMore.getScrollY());
                    if(tvLoadMore.getScrollY() + dy >0){
                        tvLoadMore.scrollBy(0,dy);
                        consumed[1] = dy;
                    }else {
                        tvLoadMore.scrollBy(0,-tvLoadMore.getScrollY());
                        //consumed[1] = -tvLoadMore.getScrollY() ;



                        if (mChildList != null && mChildList.canScrollVertically(dy + tvLoadMore.getScrollY())) {
                            //中层liearlayout消耗不完的，就交给子list消耗
                            Log.e("onNestedPreScroll","子列表能向下滑，那么就下滑子列表");
                            consumed[1] = dy;
                            mChildList.scrollBy(0, dy + tvLoadMore.getScrollY());
                        }else {
                            Log.e("onNestedPreScroll","子列表已经下滑到顶，就下滑母列表，事件被母列表接管");
                        }
                    }*/

                }else {
                    if(!mChildList.canScrollVertically(1)){
                        isBottom = false;
                        if(scrollListener != null){
                            scrollListener.onLeaveBottom(mChildList,mChildView);
                        }
                        tvLoadMore.findViewById(R.id.tv_loadmore).setVisibility(GONE);
                    }
                    if (mChildList != null && mChildList.canScrollVertically(dy)) {
                        //子列表能向下滑，那么就下滑子列表
                        Log.e("onNestedPreScroll","子列表能向下滑，那么就下滑子列表");
                        consumed[1] = dy;
                        mChildList.scrollBy(0, dy);
                    }else {
                        Log.e("onNestedPreScroll","子列表已经下滑到顶，就下滑母列表，事件被母列表接管");
                    }
                }

            }
        } else {
            Log.e("onNestedPreScroll","tablayout+viewpager列表还没在parent里置顶");
            if (childTop < dy) {
                consumed[1] = dy - childTop;
            }
            if(isTabsTop){
                isTabsTop = false;
                if(scrollListener != null){
                    scrollListener.onTabsStateChanged(isTabsTop);
                }
            }
        }
    }

    private void reduceInnerRecycleviewHeight(int dy, int[] consumed) {
        if(mChildList.getMeasuredHeight() + footHeight + 120 == pagerHeight ){
            return;
        }

        //还需要滑多远：
        int toReduce = mChildList.getMeasuredHeight() - (pagerHeight - 120 - footHeight) ;
        if(toReduce >= dy){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mChildList.getLayoutParams();
            params.height -= dy;
            mChildList.setLayoutParams(params);
            consumed[1] = dy;
        }else {
            int toConsume = toReduce;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mChildList.getLayoutParams();
            params.height -= toConsume;
            mChildList.setLayoutParams(params);
            consumed[1] = toConsume;
        }
    }

    boolean isTop = true;
    boolean isBottom = false;
    boolean isTabsTop = false;

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    ScrollListener scrollListener;

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


    public interface ScrollListener{
        void onTabsStateChanged(boolean isTabsTop);

        //void onReachedTop();

        /**
         *
         * @param recyclerView 当前page的recycleview
         * @param tabvpView tab+vp的母view，从这里可以拿到position
         */
        void onReachBottom(RecyclerView recyclerView, View tabvpView);

        void onLeaveBottom(RecyclerView recyclerView, View tabvpView);
    }

}
