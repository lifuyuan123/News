package azsecuer.zhuoxin.com.myapps.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/8/31.
 */
public class SharedpreferencesUtil {
    private Context context;
    private String lead_data = "lead_data";
    private String news_data = "news_data";

    public SharedpreferencesUtil(Context context) {
        this.context = context;
    }

    //保存信息
    public void saveSharedtodata(String versionName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(lead_data, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("versionName", versionName);
        editor.commit();//提交
    }

    //获取信息
    public String getSharedtodata() {
        return context.getSharedPreferences(lead_data, 0).getString("versionName", "");
    }


    //保存信息
    public void savaSharedptodata(Set<String> titles) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(news_data, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("menu", titles);
        editor.commit();
    }

    //获取信息
    public List<String> getSharedptodatalist() {
        Set<String> titles = getSharedptodata();
        List<String> titleslist = new ArrayList<>();
        for (String tab : titles) {
            titleslist.add(tab);
        }
        return titleslist;
    }


    public Set<String> getSharedptodata() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(news_data, 0);
        Set<String> titles = sharedPreferences.getStringSet("menu", null);
        if (titles == null) {
            titles = new TreeSet<>();
            titles.add("国内");
            titles.add("军事");
            titles.add("互联网");
            titles.add("房产");
            titles.add("汽车");
            titles.add("体育");
            titles.add("娱乐");
        }
        return titles;

    }
}
