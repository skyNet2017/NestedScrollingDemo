package com.xue.viewpagerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewTreeObserver;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xue.viewpagerdemo.NestedScrollLayout;
import com.xue.viewpagerdemo.NestedScrollLayout2;
import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.common.AdapterItem;
import com.xue.viewpagerdemo.common.BaseAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.items.PageItem;
import com.xue.viewpagerdemo.items.ParentItem;
import com.xue.viewpagerdemo.model.NestedViewModel;
import com.xue.viewpagerdemo.model.PageVO;
import com.xue.viewpagerdemo.viewholder.PagerViewHolder;
import com.xue.viewpagerdemo.viewholder.ImageViewHolder;

import java.util.ArrayList;
import java.util.List;



import static com.xue.viewpagerdemo.ViewType.TYPE_PAGER;
import static com.xue.viewpagerdemo.ViewType.TYPE_PARENT;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    private NestedViewModel viewModel;

    private NestedScrollLayout2 container;

    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_generation);
        container = findViewById(R.id.rootview);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        container.setRootList(recyclerView);
        container.setTarget(this);
        initAdapter();
        viewModel = new NestedViewModel();
        viewModel.owener = this;
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = container.getMeasuredHeight();
                viewModel.pagerHeight = height;
            }
        });

        refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setFooterHeight(30);
        refreshLayout.setEnableLoadMore(true);
        //refreshLayout.foot
    }

    private void initAdapter() {
        SparseArray<Class<? extends BaseViewHolder>> viewHolders = new SparseArray<>();
        viewHolders.put(TYPE_PARENT, ImageViewHolder.class);
        viewHolders.put(TYPE_PAGER, PagerViewHolder.class);
        int[] ids = new int[]{R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5
                ,R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5};
        List<AdapterItem> itemList = new ArrayList<>();
        for (int id : ids) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
            itemList.add(new ParentItem(bitmap));
        }
        List<PageVO> pageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pageList.add(new PageVO(Color.WHITE, "tab" + i));
        }
        itemList.add(new PageItem(pageList));
        adapter = new BaseAdapter(itemList, this, viewHolders);
        recyclerView.setAdapter(adapter);
    }
}
