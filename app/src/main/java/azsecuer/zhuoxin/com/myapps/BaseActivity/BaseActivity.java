package azsecuer.zhuoxin.com.myapps.BaseActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();//加载布局
        initView();//初始化view
        initData();//后逻辑
    }
    public abstract void setLayout();
    public abstract void initView();
    public abstract void initData();

    /**
     *
     * @param toclass
     * 跳转
     */
    public void startActivity(Class<?> toclass){
        Intent intent=new Intent(this,toclass);
        startActivity(intent);
    }
    public String getAppVersionName(){
        String versionName="";
        try {
            versionName=getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    public int getAppVersionCode(){
        int versioncode=0;
        try {
            versioncode=getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }
}
