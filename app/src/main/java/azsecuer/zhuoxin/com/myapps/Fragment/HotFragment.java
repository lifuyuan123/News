package azsecuer.zhuoxin.com.myapps.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import azsecuer.zhuoxin.com.myapps.Activity.Webview_Activity;
import azsecuer.zhuoxin.com.myapps.Adapter.Hotadapter.Myadapter;
import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.Manager.GETURL;
import azsecuer.zhuoxin.com.myapps.Manager.URLManager;
import azsecuer.zhuoxin.com.myapps.MyApplication.MyApplication;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress_;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HotFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
    public List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlistBeanList;
    private LinearLayoutManager linearLayoutManager;
    private Myadapter myadapter;
    boolean isload;
    boolean isadd=false;
    private int page = 1, allpage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contentlistBeanList=new ArrayList<>();
        myadapter = new Myadapter(getActivity());
        StringRequest("国内最新", page);
        Log.i("list", contentlistBeanList.toString());
//        myadapter.setList(contentlistBeanList);
//        recyclerView.setAdapter(myadapter);

         //跳转页面
        myadapter.setOnClick(new Myadapter.OnClick() {
            @Override
            public void click(View view, int position) {
                Intent intent=new Intent(getActivity(), Webview_Activity.class);
                Bundle bundle=new Bundle();
                contentlistBean=myadapter.list.get(position);
                bundle.putString("url",contentlistBean.getLink());
                intent.putExtras(bundle);
                startActivityForResult(intent,106);
//                startActivity(intent);
            }
        });

        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               updata();
            }
        });
        //上啦加载监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastitem=linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(lastitem==myadapter.getItemCount()-1){
                    switch (newState){
                        case 0:
                            Log.i("action","停止滑动");
                            if(isload){
                                isload=false;
                                myadapter.setType(1);//显示加载中
                                adddata();
                                myadapter.setType(0);
                            }
                            break;
                        case 1:
                            Log.i("action","开始滑动");
                            break;
                        case 2:
                            Log.i("action","没有滑动");
                            if(!isload){
                                isload=true;
                            }
                            break;
                    }
                }

            }
        });

    }
    //通过跳转接受信息将其存入数据库
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==106&&resultCode==101&&data.getBooleanExtra("needupdate",true)){
            Log.i("_____",contentlistBean.toString()+"1111");
            MyExpress_ express=new MyExpress_(getActivity());
                express.addMsg(contentlistBean.getTitle(),contentlistBean.getImageurls().get(0).getUrl(),
                        contentlistBean.getPubDate(),contentlistBean.getSource(),contentlistBean.getLink());
                Log.i("contentlistBean",contentlistBean.toString()+"1111");
        }
    }

    void updata() {//数据刷新
        StringRequest("国内最新",page);
        swipeRefreshLayout.setRefreshing(false);
        myadapter.notifyDataSetChanged();
        page=2;
    }

    void adddata() {//添加数据
        ++page;
        isadd=true;
        StringRequest("国内最新", page);
        if (allpage > 0 && page > allpage) {
            myadapter.setType(2);
        }
    }

    //数据加载
    public void StringRequest(String name, int pager) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETURL.findNewsName__(name, pager), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(s, Bean.class);
                Bean.ShowapiResBodyBean showapiResBodyBean = bean.getShowapi_res_body();
                Bean.ShowapiResBodyBean.PagebeanBean pagebeanBean = showapiResBodyBean.getPagebean();
                contentlistBeanList = pagebeanBean.getContentlist();
                allpage = pagebeanBean.getAllPages();
                if (contentlistBeanList == null || contentlistBeanList.size() == 0)
                    return;
                Iterator<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> iterator = contentlistBeanList.iterator();
                Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean temp = null;
                // 遍历数据源 并且把所有的没有图片地址的信息删除掉
                while (iterator.hasNext()) {
                    temp = iterator.next();
                    if (temp == null || temp.getImageurls().size() == 0 || temp.getImageurls().get(0).getUrl() == null)
                        iterator.remove();
                }
                if(isadd){
                    isadd=false;
                    myadapter.addall(contentlistBeanList);
                }else {
                    myadapter.setList(contentlistBeanList);
                    recyclerView.setAdapter(myadapter);
                }

                Log.i("list", "####"+contentlistBeanList.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("list", "错误了");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> apikey = new HashMap<>();
                apikey.put("apikey", GETURL.GETapikey);
                return apikey;
            }
        };
        requestQueue.add(stringRequest);
    }
}
