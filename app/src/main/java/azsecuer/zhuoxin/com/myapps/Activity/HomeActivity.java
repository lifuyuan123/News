package azsecuer.zhuoxin.com.myapps.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import azsecuer.zhuoxin.com.myapps.BaseActivity.BaseActivity;
import azsecuer.zhuoxin.com.myapps.Fragment.HotFragment;
import azsecuer.zhuoxin.com.myapps.Fragment.NewsFragment;
import azsecuer.zhuoxin.com.myapps.Fragment.SerchFragment;
import azsecuer.zhuoxin.com.myapps.R;
import cn.sharesdk.framework.ShareSDK;
import onekeyshare.OnekeyShare;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private RadioButton rb_news, rb_hot, rb_serch;
    private Fragment[] fragment = new Fragment[3];
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    private TextView textView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_home);
        ShareSDK.initSDK(this,"");
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
    @Override
    public void initView() {
        rb_news = (RadioButton) findViewById(R.id.rb_news);
        rb_hot = (RadioButton) findViewById(R.id.rb_hot);
        rb_serch = (RadioButton) findViewById(R.id.rb_serch);
        rb_news.setChecked(true);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        rb_news.setOnClickListener(this);
        rb_hot.setOnClickListener(this);
        rb_serch.setOnClickListener(this);
        choiceFragment(0);
        initToolbar();

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.tv_title);
        toolbar.setTitle("");
        textView.setText("News");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();//同步状态
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_mycollection:
                        Intent intent=new Intent(HomeActivity.this,CollectActivity.class);
                        startActivity(intent);
                        Toast.makeText(HomeActivity.this,"我的收藏",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.icon_aboutwe:
                        Toast.makeText(HomeActivity.this,"关于我们",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.icon_setting:
                        Toast.makeText(HomeActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


    }
    /**
     *  创建出右上角的按钮
     * @param menu
     * @return 注意返回值
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }
    /**
     * 对右上角的菜单有一个状态监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showShare();
        return true;
    }

    private void choiceFragment(int index) {
        hideAllFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment[index] == null) {
            switch (index) {
                case 0:
                    fragment[index] = new NewsFragment();
                    fragmentTransaction.add(R.id.framelayout, fragment[index]);
                    break;
                case 1:
                    fragment[index] = new HotFragment();
                    fragmentTransaction.add(R.id.framelayout, fragment[index]);
                    break;
                case 2:
                    fragment[index] = new SerchFragment();
                    fragmentTransaction.add(R.id.framelayout, fragment[index]);
                    break;
            }
        } else {
            fragmentTransaction.show(fragment[index]);
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragment.length; i++) {
            if (fragment[i] != null) {
                fragmentTransaction.hide(fragment[i]);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_news:
                choiceFragment(0);
                break;
            case R.id.rb_hot:
                choiceFragment(1);
                break;
            case R.id.rb_serch:
                choiceFragment(2);
                break;
        }
    }

}
