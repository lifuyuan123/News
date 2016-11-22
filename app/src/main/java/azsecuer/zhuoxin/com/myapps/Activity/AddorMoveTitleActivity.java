package azsecuer.zhuoxin.com.myapps.Activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import azsecuer.zhuoxin.com.myapps.Adapter.MyTitleAdapter;
import azsecuer.zhuoxin.com.myapps.BaseActivity.BaseActivity;
import azsecuer.zhuoxin.com.myapps.CallBack.Flowlayoutcallback;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.Ui.FlowLayout;
import azsecuer.zhuoxin.com.myapps.Util.SharedpreferencesUtil;

public class AddorMoveTitleActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private FlowLayout flowLayout;
    private List<String> data;
    private Toolbar toolbar;
    private MyTitleAdapter adapter;
    private Set<String> sharedpdata;
    private boolean needupdate=false;
    private SharedpreferencesUtil sharedpreferencesUtil;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_addor_move_title);
    }

    @Override
    public void initView() {
        listView=(ListView)findViewById(R.id.listview);
        flowLayout=(FlowLayout)findViewById(R.id.framelayout);
        toolbar=(Toolbar)findViewById(R.id.add_toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_return);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("needupdate",needupdate);
                setResult(101,intent);
                sharedpreferencesUtil.savaSharedptodata(sharedpdata);
                finish();
            }
        });
        adapter=new MyTitleAdapter(this);
        adapter.setdata(getdata());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        sharedpreferencesUtil=new SharedpreferencesUtil(this);
        sharedpdata=sharedpreferencesUtil.getSharedptodata();
        flowLayout.getSetData(sharedpdata);
        flowLayout.setCallback(new Flowlayoutcallback() {
            @Override
            public void afterOnChildClick() {
                Toast.makeText(AddorMoveTitleActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                needupdate=true;
            }
        });
    }

    public List<String> getdata() {
        data=new ArrayList<>();
        data.add("国内");
        data.add("国际");
        data.add("军事");
        data.add("财经");
        data.add("互联网");
        data.add("房产");
        data.add("汽车");
        data.add("体育");
        data.add("娱乐");
        data.add("游戏");
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(sharedpdata.add(data.get(position))){
            flowLayout.getSetData(sharedpdata);
            needupdate=true;
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sharedpreferencesUtil.savaSharedptodata(sharedpdata);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.putExtra("needupdate",needupdate);
            setResult(101,intent);
            sharedpreferencesUtil.savaSharedptodata(sharedpdata);
            finish();
            return false;
        }else
        return super.onKeyDown(keyCode, event);
    }
}
