package com.xue.viewpagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.xue.viewpagerdemo.model.NestedViewModel;
import com.xue.viewpagerdemo.model.PageVO;

import java.lang.ref.SoftReference;
import java.util.List;



/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class SubPagerAdapter extends FragmentStatePagerAdapter {

    private List<PageVO> itemList;

    public SubPagerAdapter(FragmentManager fm, List<PageVO> itemList) {
        super(fm);
        this.itemList = itemList;
    }

    public void setViewModel(NestedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private NestedViewModel viewModel;

    @Override
    public Fragment getItem(int position) {
        SubFragment fragment = new SubFragment();
        fragment.setViewModel(viewModel);
        Bundle bundle = new Bundle();
        bundle.putInt("color", itemList.get(position).getColor());
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itemList.get(position).getTitle();
    }
}
