package com.xue.viewpagerdemo.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xue.viewpagerdemo.NestedScrollLayout2;


/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class NestedViewModel  {

    public int pagerHeight;

    public void setChildView(View childView) {
        this.childView = childView;
        nestedLayout.setChildView(childView);
    }

    public void setChildList(RecyclerView childList) {
        this.childList = childList;
        nestedLayout.setChildList(childList);
    }

    public View childView;

    public RecyclerView childList;

    public Object owener;

    public void setNestedLayout(NestedScrollLayout2 nestedLayout) {
        this.nestedLayout = nestedLayout;

    }

    public NestedScrollLayout2 nestedLayout;









}
