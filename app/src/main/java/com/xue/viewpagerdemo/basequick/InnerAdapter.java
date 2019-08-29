package com.xue.viewpagerdemo.basequick;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xue.viewpagerdemo.R;

public class InnerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public InnerAdapter() {
        super(R.layout.item_text);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.textview,item);
        Log.e("InnerAdapter","convert:"+item);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        Log.e("InnerAdapter","onCreateDefViewHolder");
        return super.onCreateDefViewHolder(parent, viewType);
    }
}
