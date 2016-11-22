package azsecuer.zhuoxin.com.myapps.Manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/9/14.
 */
public class GETURL {
    public static String GETurl="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static String GETapikey="cb5e909074633518f2c4a9a75a9f9518";

    //拼接的方法
    public static String findNewsName(String name,int page){
        return GETurl+"?channelName="+ChangeUTF_8(name+"焦点")+"&page="+page;
    }
    public static String findNewsName__(String name,int page){
        return GETurl+"?channelName="+ChangeUTF_8(name)+"&page="+page;
    }
    public static String ChangeUTF_8(String name){
        try {
            name= URLEncoder.encode(name,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         return name;
    }
}
