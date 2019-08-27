package com.xue.viewpagerdemo.basequick;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.xue.viewpagerdemo.R;

import java.util.List;

public class BottomVpProvider extends BaseItemProvider<List<String>, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeMultiAdapter.TYPE_BOTTOM_VP;
    }

    @Override
    public int layout() {
        return R.layout.goods_list;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, List<String> data, int position) {







    }
}
