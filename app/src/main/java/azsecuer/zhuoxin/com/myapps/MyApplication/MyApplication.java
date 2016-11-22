package azsecuer.zhuoxin.com.myapps.MyApplication;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MyApplication extends Application {
    public static Gson gson;
    public static RequestQueue requestQueue;

    public static Gson getGson() {
        return gson;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson=new Gson();
        requestQueue=Volley.newRequestQueue(getApplicationContext());
        // 1.初始化ImageLoader配置信息 LruCache
        // ImageLoaderConfiguration configuration1 = ImageLoaderConfiguration.createDefault(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
//                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(50)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
//                .imageDownloader(new BaseImageDownloader(this))//default
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
                .writeDebugLogs()
                .build();
        // 2.将配置信息给予我们的ImageLoader对象
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ApiStoreSDK.init(this,"cb5e909074633518f2c4a9a75a9f9518");
        requestQueue.cancelAll(this);//停止所有请求
    }
}
