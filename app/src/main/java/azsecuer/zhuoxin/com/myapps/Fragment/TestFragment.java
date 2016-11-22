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
import android.widget.ListView;

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
import azsecuer.zhuoxin.com.myapps.Adapter.MytestAdapter;
import azsecuer.zhuoxin.com.myapps.Adapter.baseAdapter.BaseAdapter;
import azsecuer.zhuoxin.com.myapps.Adapter.baseAdapter.NewsContentAdapter;
import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.Bean.VolleyInfo;
import azsecuer.zhuoxin.com.myapps.Manager.GETURL;
import azsecuer.zhuoxin.com.myapps.Manager.URLManager;
import azsecuer.zhuoxin.com.myapps.MyApplication.MyApplication;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress_;

/**
 * Created by Administrator on 2016/9/1.
 */
public class TestFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String newname;
    private Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean;
    private List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlist;
    private NewsContentAdapter adapter;
    private final int UP = 0, DOWN = 1;
    private int nowpage = 1, allpage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newname = getArguments().getString("newname");
        Log.i("contentlist", newname);
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.test_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.test_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.text_color_blue,
                R.color.text_color_green);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                StringRequest(DOWN, 1);
            }
        });
        contentlist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsContentAdapter(contentlist, getActivity(), recyclerView);
        adapter.setOnloadListenr(new BaseAdapter.OnloadListenr() {
            @Override
            public void onloading() {
                StringRequest(UP, nowpage);

            }
        });
        recyclerView.setAdapter(adapter);
        StringRequest(UP, nowpage);
        adapter.setListener(new NewsContentAdapter.MyRecyclerListener() {
            @Override
            public void onMyRecyclerClickd(View view, int position) {
                Intent intent = new Intent(getActivity(), Webview_Activity.class);
                Bundle bundle = new Bundle();
                contentlistBean=adapter.myDatas.get(position);
                bundle.putString("url",contentlistBean.getLink());
                intent.putExtras(bundle);
                startActivityForResult(intent,100);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==101&&data.getBooleanExtra("needupdate",true)){
            MyExpress_ express=new MyExpress_(getActivity());
            express.addMsg(contentlistBean.getTitle(),contentlistBean.getImageurls().get(0).getUrl(),
                    contentlistBean.getPubDate(),contentlistBean.getSource(),contentlistBean.getLink());
            Log.i("contentlistBean",contentlistBean.toString()+"1111");
        }
    }

    //string消息队列
    public void StringRequest(final int getdatatype, int pager) {
        if (allpage > 0 && nowpage > allpage) {// 没有更多的数据
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingNoMore();
        }
        RequestQueue requestQueue = MyApplication.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETURL.findNewsName(newname, pager), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    Gson gson = MyApplication.getGson();
                    Bean bean = gson.fromJson(s, Bean.class);
                    Bean.ShowapiResBodyBean showapiResBodyBean = bean.getShowapi_res_body();
                    Bean.ShowapiResBodyBean.PagebeanBean pagebeanBean = showapiResBodyBean.getPagebean();
                    contentlist = pagebeanBean.getContentlist();
                    allpage = pagebeanBean.getAllPages();
                    onGetDataSuccess(getdatatype);
                } catch (Exception e) {
                    getDefult(getdatatype);
                }
                Log.i("contentlist", contentlist.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getDefult(getdatatype);
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

    private void getDefult(int getDataType) {
        switch (getDataType) {
            case UP:
                adapter.setLoadingError();
                break;
            case DOWN:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    private void onGetDataSuccess(int getdatatype) {
        if (contentlist == null || contentlist.size() == 0)
            return;
        Iterator<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> iterator = contentlist.iterator();
        Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean temp = null;
        // 遍历数据源 并且把所有的没有图片地址的信息删除掉
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp == null || temp.getImageurls().size() == 0 || temp.getImageurls().get(0).getUrl() == null)
                iterator.remove();
        }

        switch (getdatatype) {
            case UP:
                adapter.setLoadingComplete();
                if (contentlist != null)
                    adapter.addall(contentlist);
                nowpage++;
                break;
            case DOWN:
                if (contentlist != null) {
                    adapter.cleanall();
                    adapter.addall(contentlist);
                }
                swipeRefreshLayout.setRefreshing(false);
                nowpage = 2;
                break;
        }
    }
}
