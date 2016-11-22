package azsecuer.zhuoxin.com.myapps.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Activity.Webview_Activity;
import azsecuer.zhuoxin.com.myapps.Adapter.Serchadapter;
import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.SQLite.MyDb;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress_;

/**
 * Created by Administrator on 2016/9/1.
 */
public class SerchFragment extends Fragment {
    private ListView listView;
    private EditText editText;
    private MyExpress myExpress;
    private MyExpress_ myExpress_;
    private Serchadapter adapter;
    List<Info> list;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    listView.setVisibility(View.VISIBLE);
                    String str = (String) msg.obj;
                    list=myExpress_.getMsg__(str);
                    if(list.size()==0){
                       list.add(new Info("没有数据哦！","","","",""));
                    }
                        adapter=new Serchadapter(getActivity(),list);
                        listView.setAdapter(adapter);
                      break;
                case 1:
                    listView.setVisibility(View.GONE);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_serch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        myExpress=new MyExpress(getActivity());
        myExpress_=new MyExpress_(getActivity());
        editText = (EditText) view.findViewById(R.id.et_text);
        listView = (ListView) view.findViewById(R.id.lv_listview);
        editText.addTextChangedListener(new MyText());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).title!="没有数据哦！"){
                    Intent intent=new Intent(getActivity(), Webview_Activity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("url",list.get(position).url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
//        setDeta();
    }

    class MyText implements TextWatcher {
        //文本改变前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //文本改变中
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Message message = new Message();
            if(s.length()>0){
                message.what = 0;
                String str = s.toString();
                message.obj = str;
                handler.sendMessage(message);
            }else {
                handler.sendEmptyMessage(1);
            }

        }
        //文本改变后
        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
