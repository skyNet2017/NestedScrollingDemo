package com.xue.viewpagerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xue.viewpagerdemo.common.AdapterItem;
import com.xue.viewpagerdemo.common.BaseAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.common.InnerAdapter;
import com.xue.viewpagerdemo.items.TextItem;
import com.xue.viewpagerdemo.model.NestedViewModel;
import com.xue.viewpagerdemo.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 薛贤俊 on 2019/2/13.
 */
public class SubFragment extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    public void setViewModel(NestedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private NestedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goods_list, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        int color = getArguments().getInt("color");
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setBackgroundColor(color);

        SparseArray<Class<? extends BaseViewHolder>> viewHolders = new SparseArray<>();
        viewHolders.put(ViewType.TYPE_TEXT, TextViewHolder.class);
        List<TextItem> itemList = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemList.add(new TextItem("text" + i));
            strings.add("text"+i);
        }
       // adapter = new BaseAdapter(itemList, view.getContext(), viewHolders);

        //使用basequickadapter,看loadmore是否能用
        adapter = new InnerAdapter();
        ((InnerAdapter) adapter).setEnableLoadMore(true);
        recyclerView.setAdapter(adapter);
        ((InnerAdapter) adapter).addData(strings);


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && trackFragment() && viewModel != null) {
            viewModel.setChildList(recyclerView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (trackFragment() && viewModel != null) {
            viewModel.setChildList(recyclerView);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && trackFragment() && viewModel != null) {
            viewModel.setChildList(recyclerView);
        }
    }



    private boolean trackFragment() {
        if (getView() == null || !(getView().getParent() instanceof View)) {
            return false;
        }
        View parent = (View) getView().getParent();
        if (parent instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) parent;
            int currentItem = viewPager.getCurrentItem();
            //这里需要注意，SubPagerAdapter中，需要把每个Fragment的position传入Arguments
            int position = getArguments() != null ? getArguments().getInt("position", -1) : -1;
            return currentItem == position;
        }
        return false;
    }
}
