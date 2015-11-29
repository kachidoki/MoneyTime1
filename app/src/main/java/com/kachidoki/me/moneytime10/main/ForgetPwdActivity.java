package com.kachidoki.me.moneytime10.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.AccountModel;
import com.kachidoki.me.moneytime10.util.Listener.TimeListener;
import com.kachidoki.me.moneytime10.util.SMSManager;

import android.os.Handler;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by Frank on 15/11/28.
 */
public class ForgetPwdActivity extends AppCompatActivity {

    SMSManager smsManager;
    Toolbar mToolbar;
    String phone = null;
    EditText phoneT;
    EditText code;
    Button retry;
    Button ok;
    EditText password;
    int i = 60;
    boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initializeToolbar();
        phoneT = (EditText) findViewById(R.id.et_phone);
        code = (EditText) findViewById(R.id.et_code);
        retry = (Button) findViewById(R.id.tg_retry);
        ok = (Button) findViewById(R.id.tg_ok);
        password = (EditText) findViewById(R.id.et_password);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(ForgetPwdActivity.this, phoneT.getText().toString());

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPwd();
            }
        });

    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                retry.setText("重新发送(" + i-- + ")");
            } else if (msg.what == -8) {
                retry.setText("获取验证码");
                retry.setClickable(true);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initializeToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }
    }

    private void forgetPwd() {
        if (code.getText().toString().isEmpty()) {
            Toast.makeText(ForgetPwdActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            Toast.makeText(ForgetPwdActivity.this, "请输入6-12位密码", Toast.LENGTH_SHORT).show();

            return;
        }
        resetPass(code.getText().toString(), password.getText().toString());
    }

    public void resetPass(final String code, final String password) {
        BmobUser.resetPasswordBySMSCode(this, code, password, new ResetPasswordByCodeListener() {

            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {
                    Toast.makeText(ForgetPwdActivity.this, "重置成功", Toast.LENGTH_SHORT).show();
                    Log.i("smile", "密码重置成功");
                    AccountModel.getInstance().login(ForgetPwdActivity.this,phoneT.getText().toString(),password);
                    finish();
                } else {
                    Log.i("smile", "重置失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                }
            }
        });
    }


    private  void send(Context context,String number){
        if (number!=null&&number!=""){
            BmobSMS.requestSMSCode(context, number,"模板名称", new RequestSMSCodeListener() {

                @Override
                public void done(Integer integer, BmobException e) {
                    if(e==null){//验证码发送成功
                        retry.setClickable(false);
                        retry.setText("重新发送(" + i-- + ")");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 60; i > 0; i--) {
                                    handler.sendEmptyMessage(-9);
                                    if (i <= 0) {
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                handler.sendEmptyMessage(-8);
                            }
                        }).start();
                        Log.i("smile", "短信id：" + integer);//用于查询本次短信发送详情
                        Toast.makeText(ForgetPwdActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(ForgetPwdActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }

    }

    public void reSendMessage(String phone) {
        this.phone = phone;
        smsManager.sendMessage(ForgetPwdActivity.this, phone);
    }


}
