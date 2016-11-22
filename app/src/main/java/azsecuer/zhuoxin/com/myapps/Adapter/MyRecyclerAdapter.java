package azsecuer.zhuoxin.com.myapps.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> lists;
    private RecyclerListener listener;
    private List<Integer> heigths;

    public RecyclerListener getListener() {
        return listener;
    }

    public void setListener(RecyclerListener listener) {
        this.listener = listener;
    }

    public interface RecyclerListener{
        void onRecycleritemclick(View view,int position);
        void onRecycleritemLongclick(View view,int positon);

    }
    //高度随机操作
    public MyRecyclerAdapter(Context context,List<String> lists) {
        this.context=context;
        this.lists=lists;
        layoutInflater=LayoutInflater.from(context);
        this.heigths=new ArrayList<>();
        for(int i=0;i<lists.size();i++){
         heigths.add((int)(100+Math.random()*350));
        }
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.test_recyclerview,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams=((MyViewHolder)holder).textView.getLayoutParams();
        layoutParams.height=heigths.get(position);
        ((MyViewHolder)holder).textView.setLayoutParams(layoutParams);
        ((MyViewHolder)holder).textView.setText(lists.get(position));
        if (listener!=null){
            ((MyViewHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRecycleritemclick(((MyViewHolder)holder).textView,position);
                }
            });
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.tv_recyclerview);
        }
    }
}
