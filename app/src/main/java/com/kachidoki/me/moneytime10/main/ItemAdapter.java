package com.kachidoki.me.moneytime10.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ListPopupWindow;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;
import com.kachidoki.me.moneytime10.util.PopupWindowsUtils;
import com.kachidoki.me.moneytime10.util.Util;

/**
 * Created by Frank on 15/8/12.
 */
public class ItemAdapter extends RecyclerArrayAdapter<ItemBean> {

    private ListPopupWindow mpopWindows;

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
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mpopWindows = PopupWindowsUtils.createTextListPopupWindows(getContext(), new String[]{"删除"}, new PopupWindowsUtils.PopupListener() {
                    @Override
                    public void onListenerPop(ListPopupWindow listp) {

                    }

                    @Override
                    public void onListItemClickBack(ListPopupWindow popupWindow, View parent, final int i) {
                        mpopWindows.dismiss();
                        switch (i) {
                            case 0:
                                Log.i("POP", "删除");
                                new MaterialDialog.Builder(getContext())
                                        .content("您真的要删除这条记录么？")
                                        .positiveText("删除")
                                        .negativeText("取消")
                                        .callback(new MaterialDialog.ButtonCallback() {
                                            @Override
                                            public void onPositive(MaterialDialog dialog) {
                                                SQLiteDatabase db = MyDatebaseHelper.getInstance(getContext()).getReadableDatabase();
                                                ItemModel.getInstance().delete(db, getItem(position).getYear(), getItem(position).getMonth(), getItem(position).getDay(), getItem(position).getStartTime(), getItem(position).getEndTime());
                                                remove(getItem(position));
                                                Util.Toast(getContext(), "删除成功");

                                            }
                                        }).show();
                                break;

                        }
                    }
                });
                mpopWindows.setAnchorView(holder.itemView);
                mpopWindows.setWidth(Util.dip2px(getContext(), 108));
                mpopWindows.setVerticalOffset(Util.dip2px(getContext(), 8));
                mpopWindows.show();
                return false;
            }
        });
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
