package com.xue.viewpagerdemo.common;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;



/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void initViews();

    public abstract void bindView(T data);


}
