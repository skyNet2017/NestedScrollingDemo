package com.xue.viewpagerdemo.basequick;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ImgBean implements MultiItemEntity {

    public int img;
    @Override
    public int getItemType() {
        return HomeMultiAdapter.TYPE_BOTTOM_IMG;
    }
}
