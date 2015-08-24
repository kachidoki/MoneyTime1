package com.kachidoki.me.moneytime10.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.Position;

/**
 * Created by Frank on 15/8/12.
 */
public class ItemAdapter extends RecyclerArrayAdapter<ItemBean> {


    public ItemAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup viewGroup, int i) {
        BaseViewHolder<ItemBean> vh = null;
        switch (i){
            case 1:
                vh =  new ItemViewHolder(viewGroup);
                break;
            case 2:vh =  new ItemViewHolder2(viewGroup);
                break;
        }
        return vh;
    }

    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);

    }

    @Override
    public int getViewType(int position) {
        if (this.getItem(position).getColor().equals("black")){
            return 2;
        }else {
            return 1;
        }
    }
}
