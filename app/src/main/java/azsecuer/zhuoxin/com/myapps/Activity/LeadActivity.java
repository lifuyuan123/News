package azsecuer.zhuoxin.com.myapps.Activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Adapter.LeadPagerAdapter;
import azsecuer.zhuoxin.com.myapps.BaseActivity.BaseActivity;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.Util.SharedpreferencesUtil;

public class LeadActivity extends BaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private ViewPager viewPager;
    private Button button;
    private List<View> views;
    private Animation animation;
    @Override
    public void setLayout() {
        setContentView(R.layout.activity_lead);
        views=new ArrayList<>();
        views.add(getLayoutInflater().inflate(R.layout.lead_1,null));
        views.add(getLayoutInflater().inflate(R.layout.lead_2,null));
        views.add(getLayoutInflater().inflate(R.layout.lead_3,null));
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_lead);
        button = (Button) findViewById(R.id.bt_lead);
        animation= AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        button.setAnimation(animation);
        button.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        LeadPagerAdapter pagerAdapter = new LeadPagerAdapter(views);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_lead:
                SharedpreferencesUtil sharedpreferencesUtil=new SharedpreferencesUtil(this);
                sharedpreferencesUtil.saveSharedtodata(getAppVersionName());
                startActivity(LogoActivity.class);
                finish();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
     if(position==2){
         button.setVisibility(View.VISIBLE);
     }else {
         button.setVisibility(View.GONE);

     }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
