package com.xue.viewpagerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewTreeObserver;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xue.viewpagerdemo.NestedScrollLayout;
import com.xue.viewpagerdemo.NestedScrollLayout2;
import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.basequick.HomeMultiAdapter;
import com.xue.viewpagerdemo.basequick.ImgBean;
import com.xue.viewpagerdemo.common.AdapterItem;
import com.xue.viewpagerdemo.common.BaseAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.items.PageItem;
import com.xue.viewpagerdemo.items.ParentItem;

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

    public NestedScrollLayout2 getViewModel() {
        return container;
    }

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
        container.measurePagerHeightAtFirstTime();


        initAdapter();
        //initAdapter2();



        refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setFooterHeight(30);
        refreshLayout.setEnableLoadMore(false);
        //refreshLayout.foot
    }

   /* private void initAdapter2() {
        List<MultiItemEntity> datas = new ArrayList<>();
        adapter = new HomeMultiAdapter(datas);

        //img
        int[] ids = new int[]{R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5
                ,R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5};
        for (int id : ids) {
            ImgBean imgBean = new ImgBean();
            imgBean.img = id;
            datas.add(imgBean);
        }

        //底部vp,后续要改成异步
        BottomVpBean vpBean = new BottomVpBean();
        for (int i = 0; i < 10; i++) {
            vpBean.titles.add("tab" + i);
        }
        datas.add(vpBean);

        adapter.addData(datas);


    }*/

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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.w("out","onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.w("out","onScrolled");
            }
        });
    }
}
