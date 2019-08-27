package com.xue.viewpagerdemo.basequick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class HomeMultiAdapter extends MultipleItemRvAdapter<MultiItemEntity, BaseViewHolder> {


    public static final int TYPE_BOTTOM_IMG = 1;
    public static final int TYPE_BOTTOM_VP = 2;



    public HomeMultiAdapter(@Nullable List<MultiItemEntity> data) {
        super(data);
    }

    @Override
    protected int getViewType(MultiItemEntity multiItemEntity) {
        return multiItemEntity.getItemType();
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new ImgProvider());
        mProviderDelegate.registerProvider(new BottomVpProvider());
    }
}
