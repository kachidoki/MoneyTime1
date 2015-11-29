package com.kachidoki.me.moneytime10.main;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.kachidoki.me.moneytime10.R;
import com.kachidoki.me.moneytime10.model.AccountModel;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView img;
    private TextView username;
    private ViewPager viewPager;
    private Main_Fragment fragment1;
//    private Chart_Fragment fragment2;
    private Table_Fragment fragment3;
    private Mchart_Fragment fragment2;
    private List<Fragment> fragments;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("life", "main_activity is on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        UmengUpdateAgent.update(MainActivity.this);
        UmengUpdateAgent.forceUpdate(MainActivity.this);
        Bmob.initialize(this, "c2ba9cabeb5381f2ad272531fd6d0252");
        InitViewPager();
        InitDraw();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (AccountModel.getInstance().isLogin(this)){
            username.setText((String) BmobUser.getObjectByKey(this,"username"));
        }else {
            username.setText("用户还未登录");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true ;
            case R.id.action_settings:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

        }
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this,AboutActivity.class));
//            return true;
//        }
//        if (id == R.id.action_update) {
//            UmengUpdateAgent.forceUpdate(this);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void InitDraw(){
        img = (ImageView) findViewById(R.id.id_userimg);
        username = (TextView) findViewById(R.id.id_username);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        setupDrawerContent(mNavigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add:
                        viewPager.setCurrentItem(0);
                        Log.i("viewpager",viewPager.getCurrentItem()+"");
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_time:
                        viewPager.setCurrentItem(1);
                        Log.i("viewpager", viewPager.getCurrentItem() + "");
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_week:
                        viewPager.setCurrentItem(2);
                        Log.i("viewpager", viewPager.getCurrentItem() + "");
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this).
                                title("注销登录").
                                content("您确定要退出登录吗？").
                                positiveText("注销").
                                negativeText("取消").
                                callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        AccountModel.getInstance().logout(MainActivity.this);
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        mDrawerLayout.closeDrawers();
                        username.setText("用户还未登录");
                        break;
                }
                return true;
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AccountModel.getInstance().isLogin(MainActivity.this)){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

        if (AccountModel.getInstance().isLogin(this)){
            username.setText((String) BmobUser.getObjectByKey(this, "username" ));
        }else {
            username.setText("用户还未登录");
        }
    }
    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void InitViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragment1 = new Main_Fragment();
        fragment2 = new Mchart_Fragment();
        fragment3 = new Table_Fragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));


    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
