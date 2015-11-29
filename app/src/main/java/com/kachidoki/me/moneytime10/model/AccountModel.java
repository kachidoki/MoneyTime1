package com.kachidoki.me.moneytime10.model;

import android.content.Context;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Frank on 15/11/25.
 */
public class AccountModel {
//    public static final String FILE_ACCOUNT = "Account";

    private static final AccountModel instance = new AccountModel();
    public static AccountModel getInstance(){
        return instance;
    }
//    public Account userAccountData = null;


    public interface SucessCallback{
        void login();
    }

    private SucessCallback callback;

    public void setCallBack(SucessCallback callBack){
        this.callback = callBack;
    }

    public boolean isLogin(Context context){
        BmobUser bmobUser = BmobUser.getCurrentUser(context);
        if (bmobUser!=null){
            return true;
        }else {
            return false;
        }
    }

    public BmobUser getAccount(Context context){
        BmobUser bmobUser = BmobUser.getCurrentUser(context);
        if(bmobUser != null){
            return bmobUser;
        }else{
            return null;
        }
    }

//    void saveAccount(Account account){
//        if (account == null){
//            JFileManager.getInstance().getFolder(Dir.Object).deleteChild(FILE_ACCOUNT);
//        }else{
//            JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(account, FILE_ACCOUNT);
//        }
//    }
//
//    void setAccount(Account account){
//        userAccountData = account;
//    }

    public void register(final Context context,String number,String password){
        BmobUser user = new BmobUser();
        user.setMobilePhoneNumber(number);
        user.setUsername(number);
        user.setMobilePhoneNumberVerified(true);
        user.setPassword(password);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "注册失败:" + s, Toast.LENGTH_SHORT);
            }
        });
    }

    public void login(final Context context,String number,String password){
        BmobUser bu2 = new BmobUser();
        bu2.setPassword(password);
        bu2.setUsername(number);
        bu2.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                callback.login();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "登录失败:" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(Context context){
        BmobUser.logOut(context);   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(context); // 现在的currentUser是null了
    }

}
