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
import android.widget.Toast;

import com.kachidoki.me.moneytime10.R;

/**
 * Created by Frank on 15/11/21.
 */
public class RegisterActivity extends AppCompatActivity {

    Toolbar mToolbar;
    EditText number;
    EditText password;
    EditText passwordRe;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        passwordRe = (EditText) findViewById(R.id.password_re);
        register = (Button) findViewById(R.id.register);

        initializeToolbar();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               if(checkInput()==true){
                   Intent i = new Intent(RegisterActivity.this, RegisterVerifyActivity.class);
                   i.putExtra("number",number.getText().toString() );
                   i.putExtra("password", password.getText().toString());
                   startActivityForResult(i, 100);
//               }
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    public boolean checkInput() {
        if (number.getText().toString().length() != 11) {
            Toast.makeText(RegisterActivity.this, "请输入正确手机号",Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            Toast.makeText(RegisterActivity.this, "请输入6-12位密码",Toast.LENGTH_SHORT).show();

            return false;
        }else if (!passwordRe.getText().toString().equals(password.getText().toString())){
            Toast.makeText(RegisterActivity.this, "两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
        //检查电话


    }
}
