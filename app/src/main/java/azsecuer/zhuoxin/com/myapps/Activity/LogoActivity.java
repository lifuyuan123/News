package azsecuer.zhuoxin.com.myapps.Activity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import azsecuer.zhuoxin.com.myapps.BaseActivity.BaseActivity;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.Util.SharedpreferencesUtil;

public class LogoActivity extends BaseActivity implements Animation.AnimationListener {
    private ImageView imageView;
    private Animation animation;
    private SharedpreferencesUtil sharedpreferencesUtil;

    @Override
    public void setLayout() {
        sharedpreferencesUtil = new SharedpreferencesUtil(this);
        String versionName = sharedpreferencesUtil.getSharedtodata();
        if (versionName.equals("") || !versionName.equals(getAppVersionName())) {
            startActivity(LeadActivity.class);
            finish();
        }
        setContentView(R.layout.activity_logo);
    }

    @Override
    public void initView() {
        imageView=(ImageView)findViewById(R.id.iv_logo);
        animation=AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        imageView.setAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
       startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
