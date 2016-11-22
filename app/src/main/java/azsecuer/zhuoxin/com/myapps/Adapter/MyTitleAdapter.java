package azsecuer.zhuoxin.com.myapps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyTitleAdapter extends BaseAdapter {
    private List<String> lists=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    public  MyTitleAdapter(Context context){
        this.context=context;
        this.layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHander viewHander=null;
        if(convertView==null){
            viewHander=new ViewHander();
            convertView=layoutInflater.inflate(R.layout.arrayadapterlayout,null);
            viewHander.textView=(TextView)convertView.findViewById(R.id.tv_array);
            convertView.setTag(viewHander);
        }else {
            viewHander=(ViewHander)convertView.getTag();
        }
        viewHander.textView.setText(lists.get(position));
        return convertView;
    }
    class ViewHander{
        TextView textView;
    }

    public void setdata(List<String> lists){
        this.lists=lists;
    }
}
