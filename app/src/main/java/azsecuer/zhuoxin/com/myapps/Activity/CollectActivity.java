package azsecuer.zhuoxin.com.myapps.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Adapter.MytestAdapter;
import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress_;

public class CollectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private Toolbar toolbar;
    private List<Info> list=new ArrayList<>();
    private MyExpress_ myExpress;
    private MytestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        myExpress=new MyExpress_(this);
        toolbar= (Toolbar)findViewById(R.id.collect_toolbar);
        listView = (ListView) findViewById(R.id.collect_list);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list=myExpress.getMsg();
        Log.i("list-----",list.toString());
        adapter=new MytestAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        adapter.setTextnoOclick(new MytestAdapter.TextnoOclick() {
            @Override
            public void callback(View v,final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CollectActivity.this);
                builder.setTitle("删除").setMessage("确定删除？"+"\n"+list.get(position).title)
                        .setNegativeButton("取消",null).
                        setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myExpress.remove(list.get(position).title);
                                list.remove(list.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Intent intent=new Intent(this,Webview_Activity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",list.get(position).url);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
