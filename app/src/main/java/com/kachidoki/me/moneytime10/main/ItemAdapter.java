package com.kachidoki.me.moneytime10.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.ItemModel;
import com.kachidoki.me.moneytime10.model.bean.ItemBean;
import com.kachidoki.me.moneytime10.util.MyDatebaseHelper;
import com.kachidoki.me.moneytime10.util.PopupWindowsUtils;
import com.kachidoki.me.moneytime10.util.Position;
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
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notifyDataSetChanged();
//                LinearLayout jinzhi = (LinearLayout) holder.itemView.findViewById(R.id.jinzhi);
//                AnimatorSet animSet1 = new AnimatorSet();
//                AnimatorSet animSet2 = new AnimatorSet();
//                ObjectAnimator animator1 = ObjectAnimator.ofFloat(jinzhi, "translationX", 0F, -110F);
//                ObjectAnimator animator2 = ObjectAnimator.ofFloat(jinzhi, "alpha", 0f, 1f);
//                ObjectAnimator animator3 = ObjectAnimator.ofFloat(jinzhi, "translationX", -110F, 0F);
//                ObjectAnimator animator4 = ObjectAnimator.ofFloat(jinzhi, "alpha", 1f, 0f);
//                animSet1.setInterpolator(new LinearInterpolator());
//                animSet1.playTogether(animator1, animator2);
//                animSet2.playTogether(animator3, animator4);
//                animSet1.setDuration(2500);
//                animSet2.setDuration(2500);
//                Log.i("Test", Util.px2dip(getContext(), jinzhi.getX()) + "   before");
//                if (position == Position.getInstance().getPostion()) {
//                    Log.i("adapther", Position.getInstance().getPostion() + "");
//                    animSet2.start();
//                    Position.getInstance().setPostion(-1);
//                    Log.i("adapther", "position is right");
//                    Log.i("Test", Util.px2dip(getContext(), jinzhi.getX()) + "   after");
//                } else {
//                    if (Position.getInstance().getPostion() == -1) {
//                        Log.i("adapther", Position.getInstance().getPostion() + "");
//                        animSet1.start();
//                        Position.getInstance().setPostion(position);
//                        Log.i("adapther", "position is -1");
//                        Log.i("Test", Util.px2dip(getContext(), jinzhi.getX()) + "   after");
//                    } else {
//                        notifyDataSetChanged();
//                        Log.i("adapther", Position.getInstance().getPostion() + "");
//                        Log.i("adapther", "positon is wrong");
////                        animSet1.start();
//                        Position.getInstance().setPostion(position);
//                        Log.i("Test", Util.px2dip(getContext(), jinzhi.getX()) + " wrong  after");
//                    }
//
//                }
//                Log.i("adapther", Position.getInstance().getPostion() + "");
//            }
//        });

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
