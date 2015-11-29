package com.kachidoki.me.moneytime10.main;

import android.app.Activity;
import android.content.Intent;
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


/**
 * Created by Frank on 15/11/18.
 */
public class LoginActivity extends AppCompatActivity {
    public static final int REGISTER = 1000;
    Toolbar mToolbar;
    EditText number;
    EditText password;
    Button login;
    TextView register;
    TextView forgetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        forgetpwd = (TextView) findViewById(R.id.find_password);

        initializeToolbar();
        AccountModel.getInstance().setCallBack(new AccountModel.SucessCallback() {
            @Override
            public void login() {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountModel.getInstance().login(LoginActivity.this, number.getText().toString(), password.getText().toString());
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REGISTER);
            }
        });

        forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPwdActivity.class));
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGISTER && resultCode == Activity.RESULT_OK){
            finish();
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
}
