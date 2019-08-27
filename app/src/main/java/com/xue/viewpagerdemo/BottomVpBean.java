package com.xue.viewpagerdemo;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xue.viewpagerdemo.basequick.HomeMultiAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomVpBean implements MultiItemEntity {
    @Override
    public int getItemType() {
        return HomeMultiAdapter.TYPE_BOTTOM_VP;
    }

    List<String> titles = new ArrayList<>();

}
