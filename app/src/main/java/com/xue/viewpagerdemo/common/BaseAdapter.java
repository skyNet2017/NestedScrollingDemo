package com.xue.viewpagerdemo.common;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;



import com.xue.viewpagerdemo.items.PageItem;

/**
 * Created by 薛贤俊 on 2019/2/21.
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<AdapterItem> itemList;

    private Context context;

    private SparseArray<Class<? extends BaseViewHolder>> viewHolders;

    private LayoutInflater inflater;

    private boolean isOuter;

    public BaseAdapter(List<AdapterItem> itemList, Context context, SparseArray<Class<? extends BaseViewHolder>> viewHolders) {
        this.itemList = itemList;
        this.context = context;
        this.viewHolders = viewHolders;
        this.inflater = LayoutInflater.from(context);
        if(itemList.get(0) instanceof PageItem){
            isOuter = true;
        }
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.w("onCreateViewHolder",isOuterText()+":"+viewType);
        try {
            Class clazz = viewHolders.get(viewType);
            HolderAnnotation annotation = (HolderAnnotation) clazz.getAnnotation(HolderAnnotation.class);
            int layoutId = annotation.layoutId();
            View itemView = inflater.inflate(layoutId, viewGroup, false);
            Constructor constructor = clazz.getConstructor(View.class);
            BaseViewHolder viewHolder = (BaseViewHolder) constructor.newInstance(itemView);
            viewHolder.initViews();
            return viewHolder;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        AdapterItem item = itemList.get(position);
        baseViewHolder.bindView(item.getDataModel());
        Log.w("onBindViewHolder",isOuterText()+": position:"+position);
    }

    private String isOuterText() {
        return isOuter ? "out" : "innner";
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
