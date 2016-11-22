package azsecuer.zhuoxin.com.myapps.Activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Adapter.MyRecyclerAdapter;
import azsecuer.zhuoxin.com.myapps.BaseActivity.BaseActivity;
import azsecuer.zhuoxin.com.myapps.R;

public class TestActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<String> list;
    private Toolbar toolbar;
    private MyRecyclerAdapter adapter;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar_test);
        toolbar.setTitle("Test");
        setSupportActionBar(toolbar);
        //水平方向
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //瀑布流
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager=new GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//删除添加动画，系统默认可写可不写
        initlist();
        adapter = new MyRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
        //回调方法  重写的实行内容
        adapter.setListener(new MyRecyclerAdapter.RecyclerListener() {
            @Override
            public void onRecycleritemclick(View view, int position) {
                Toast.makeText(TestActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onRecycleritemLongclick(View view, int positon) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager layoutManager3 = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager layoutManager4=new GridLayoutManager(this,4,LinearLayoutManager.HORIZONTAL,false);
        StaggeredGridLayoutManager layoutManager4 = new StaggeredGridLayoutManager(15, LinearLayoutManager.HORIZONTAL);
        switch (item.getItemId()) {
            case R.id.test1:
                recyclerView.setLayoutManager(layoutManager2);
                break;
            case R.id.test2:
                recyclerView.setLayoutManager(layoutManager1);
                break;
            case R.id.test3:
                recyclerView.setLayoutManager(layoutManager4);
                break;
            case R.id.test4:
                recyclerView.setLayoutManager(layoutManager3);
                break;
            case R.id.add:
                list.add(1, "insert");
                adapter.notifyItemInserted(1);
                break;
            case R.id.move:
                list.remove(1);
                adapter.notifyItemRemoved(1);
                break;
        }
        return true;
    }

    private void initlist() {
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item" + i);
        }
    }

    @Override
    public void initData() {

    }
}
