package com.xue.viewpagerdemo.viewholder;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.xue.viewpagerdemo.R;
import com.xue.viewpagerdemo.SubPagerAdapter;
import com.xue.viewpagerdemo.common.BaseViewHolder;
import com.xue.viewpagerdemo.common.HolderAnnotation;
import com.xue.viewpagerdemo.model.NestedViewModel;
import com.xue.viewpagerdemo.model.PageVO;

import java.util.List;



/**
 * Created by 薛贤俊 on 2019/2/21.
 */
@HolderAnnotation(layoutId = R.layout.item_pager)
public class PagerViewHolder extends BaseViewHolder<List<PageVO>> {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private SubPagerAdapter pagerAdapter;

    private List<PageVO> model;

    public void setViewModel(NestedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private NestedViewModel viewModel;

    private Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onChanged(Integer height) {
            if (height != null) {
                itemView.getLayoutParams().height = height;
                itemView.requestLayout();
            }
        }
    };

    public PagerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void initViews() {
        viewPager = itemView.findViewById(R.id.viewpager);
        tabLayout = itemView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                viewPager.requestLayout();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                if (viewModel != null) {
                    viewModel.childView = itemView;
                }
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                if (viewModel != null) {
                    viewModel.childView = null;
                }
            }
        });
    }

    @Override
    public void bindView(List<PageVO> data) {
        if (model == data) {
            return;
        }
        model = data;
        Context context = itemView.getContext();
        if (context instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) context;
            pagerAdapter = new SubPagerAdapter(fragmentActivity.getSupportFragmentManager(), model);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
            //viewModel = ViewModelProviders.of(fragmentActivity).get(NestedViewModel.class);
            pagerAdapter.setViewModel(viewModel);
            /*viewModel.getPagerHeight().removeObserver(observer);
            viewModel.getPagerHeight().observe(fragmentActivity, observer);*/
            if (viewModel.pagerHeight != 0)
                itemView.getLayoutParams().height = viewModel.pagerHeight;
        }
    }
}
