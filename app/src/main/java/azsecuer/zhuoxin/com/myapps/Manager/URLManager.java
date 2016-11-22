package azsecuer.zhuoxin.com.myapps.Manager;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import azsecuer.zhuoxin.com.myapps.MyApplication.MyApplication;
import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class URLManager {
    private static URLManager urlManager;
    public Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private URLManager() {
    }

    public static URLManager getInstence() {
        if(urlManager==null){
            urlManager = new URLManager();
        }
        return urlManager;
    }

    public interface Callback {
        void onSuccess(String s);

        void onEerro(VolleyError volleyError);

    }
   //string消息队列
    public void StringRequest(String urlstr) {
        RequestQueue requestQueue = MyApplication.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlstr, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (callback != null) {
                    callback.onSuccess(s);
                    Log.i("contentlist",s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callback != null) {
                    callback.onEerro(volleyError);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> apikey = new HashMap<>();
                apikey.put("apikey", GETURL.GETapikey);
                return apikey;
            }
        };
        stringRequest.setTag(urlstr);
        requestQueue.add(stringRequest);
    }
//  图片缓存机制
   class MyLrucache implements ImageLoader.ImageCache{
       private LruCache<String,Bitmap> lruCache;
       public MyLrucache() {
           super();
           int lruMax=(int)Runtime.getRuntime().maxMemory()/5;
           lruCache=new LruCache<String,Bitmap>(lruMax){
               @Override
               protected int sizeOf(String key, Bitmap value) {
                   return value.getByteCount();
               }
           };
       }
       @Override
       public Bitmap getBitmap(String s) {
           return lruCache.get(s);
       }
       @Override
       public void putBitmap(String s, Bitmap bitmap) {
           lruCache.put(s,bitmap);
       }
   }
    //带有缓存机制的图片消息队列
    public void ImagLoader(String urlimagstr, ImageView imageView){
      RequestQueue requestQueue=MyApplication.getRequestQueue();
       ImageLoader imageLoader=new ImageLoader(requestQueue,new MyLrucache() );
        ImageLoader.ImageListener imageListener=ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher,R.mipmap.ic_launcher);
    }
}
