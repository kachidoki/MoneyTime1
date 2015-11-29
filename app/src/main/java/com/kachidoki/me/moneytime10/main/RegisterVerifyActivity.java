package com.kachidoki.me.moneytime10.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.AccountModel;
import com.kachidoki.me.moneytime10.util.Listener.TimeListener;
import com.kachidoki.me.moneytime10.util.SMSManager;

import cn.smssdk.SMSSDK;

/**
 * Created by Frank on 15/11/21.
 */
public class RegisterVerifyActivity extends AppCompatActivity implements TimeListener {
    SMSManager smsManager;
    Toolbar mToolbar;
    EditText code;
    Button retry;
    Button register;
    TextView numberT;
    private String number;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerverify);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        retry = (Button) findViewById(R.id.retry);
        register = (Button) findViewById(R.id.registerSucess);
        numberT = (TextView) findViewById(R.id.number);
        code = (EditText) findViewById(R.id.code);
        number = this.getIntent().getStringExtra("number");
        password = this.getIntent().getStringExtra("password");

        initializeToolbar();

        smsManager = new SMSManager();
        smsManager.registerTimeListener(this);
        smsManager.sendMessage(this, number);


        smsManager.setCallBack(new SMSManager.SendCallback() {
            @Override
            public void send() {

                AccountModel.getInstance().register(RegisterVerifyActivity.this,number,password);
                Toast.makeText(RegisterVerifyActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();

            }

            @Override
            public void error() {
                Toast.makeText(RegisterVerifyActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
            }
        });

        numberT.setText("验证码已发送"+"到"+number);
        retry.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSendMessage();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(checkInput()==true){
                    SMSSDK.submitVerificationCode("86", number, code.getText().toString());
//                }


            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSManager.getInstance().destroy();
    }

    public void reSendMessage() {
        smsManager.sendMessage(this, number);
    }

    public boolean checkInput() {
        if (code.getText().toString().isEmpty()) {
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }


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
    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0)
            retry.setText(lastSecond + "秒后重新发送");
        else
            retry.setText("重新发送");
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        retry.setEnabled(valuable);
        retry.setBackgroundColor(getResources().getColor(valuable ? R.color.colorPrimary : R.color.Gray));
    }
}
