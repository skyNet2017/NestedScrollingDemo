package com.xue.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xue.viewpagerdemo.common.BaseAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.basequick.InnerAdapter;
import com.xue.viewpagerdemo.items.TextItem;
import com.xue.viewpagerdemo.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by 薛贤俊 on 2019/2/13.
 */
public class SubFragment extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    public void setViewModel(NestedScrollLayout2 viewModel) {
        this.viewModel = viewModel;
    }

    private NestedScrollLayout2 viewModel;

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
        for (int i = 0; i < 10; i++) {
            itemList.add(new TextItem("text" + i));
            strings.add("text"+i);
        }
        /*adapter = new BaseAdapter(itemList, view.getContext(), viewHolders);
        recyclerView.setAdapter(adapter);*/

        //使用basequickadapter,看loadmore是否能用
        adapter = new InnerAdapter();
        ((InnerAdapter) adapter).setEnableLoadMore(false);
        recyclerView.setAdapter(adapter);
        ((InnerAdapter) adapter).addData(strings);


        //loadmore
       /* final TextView textView = new TextView(getContext());
        textView.setText("loading.....");

        ((InnerAdapter) adapter).addFooterView(textView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.w("inner","onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.w("inner","onScrolled");
                if(isVisibleToUser){
                    int[] location = new int[2];
                    textView.getLocationOnScreen(location);
                    int y = location[1];
                    Log.e("inner","footview 位置："+location[0]+","+location[1]);
                    if(y == 0){
                        Log.e("inner","划出了屏幕，看不到footview");
                        return;
                    }
                    *//*if(y <= ScreenUtil.getScreenHeight()){
                     Log.e("inner","滑动到底部看到了footview");
                    }else {
                        Log.e("inner","划出了屏幕，看不到footview");
                    }*//*
                    Log.e("inner","滑动到底部看到了footview");
                    if(isLoadingMore){
                        Log.e("inner","is loadingmore....");
                        return;
                    }else {
                        Log.e("inner","start loadmore ....");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isLoadingMore = false;
                            }
                        },2000);
                    }
                }
            }
        });*/

        ((InnerAdapter) adapter).setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.w("InnerAdapter","onLoadMoreRequested");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((InnerAdapter) adapter).loadMoreComplete();
                    }
                },2000);
            }
        },recyclerView);




    }

    boolean isLoadingMore;
    boolean isVisibleToUser;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
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
