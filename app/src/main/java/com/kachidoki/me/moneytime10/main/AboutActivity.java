package com.kachidoki.me.moneytime10.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kachidoki.me.moneytime10.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Frank on 15/8/25.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }



    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
