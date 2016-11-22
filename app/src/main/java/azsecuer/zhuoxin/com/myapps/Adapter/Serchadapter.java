package azsecuer.zhuoxin.com.myapps.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.R;


/**
 * Created by Administrator on 2016/9/22.
 */
public class Serchadapter extends BaseAdapter{
    private List<Info> infos=new ArrayList<>();
    private Context context;

    public Serchadapter(Context context, List<Info> infos) {
        this.context = context;
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.serch_item,null);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.item_text);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.item_iv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(infos.get(position).title);
        return convertView;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
