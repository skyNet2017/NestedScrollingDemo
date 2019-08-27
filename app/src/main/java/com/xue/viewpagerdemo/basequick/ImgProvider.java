package com.xue.viewpagerdemo.basequick;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.xue.viewpagerdemo.R;

public class ImgProvider extends BaseItemProvider<ImgBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeMultiAdapter.TYPE_BOTTOM_IMG;
    }

    @Override
    public int layout() {
        return R.layout.item_parent_holder;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, ImgBean data, int position) {
        helper.setImageResource(R.id.imageview,data.img);
    }
}
