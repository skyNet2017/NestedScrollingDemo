package com.xue.viewpagerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class MySamrtRefreshLayout extends SmartRefreshLayout {
    public MySamrtRefreshLayout(Context context) {
        super(context);
    }

    public MySamrtRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int computeNestScrollV(@NonNull View target, int dx, int dy, @NonNull int[] consumed){
        /*int consumedY = 0;

        // dy * mTotalUnconsumed > 0 表示 mSpinner 已经拉出来，现在正要往回推
        // mTotalUnconsumed 将要减去 dy 的距离 再计算新的 mSpinner
        if (dy * mTotalUnconsumed > 0) {
            if (Math.abs(dy) > Math.abs(mTotalUnconsumed)) {
                consumedY = mTotalUnconsumed;
                mTotalUnconsumed = 0;
            } else {
                consumedY = dy;
                mTotalUnconsumed -= dy;
            }
            moveSpinnerInfinitely(mTotalUnconsumed);
        }
        return consumedY;*/

       // if(mRefreshFooter != null && mRefreshFooter.getView().is)
        return 0;


    }
}
