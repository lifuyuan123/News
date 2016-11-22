package azsecuer.zhuoxin.com.myapps.Adapter.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/18.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<T> lists;
    protected LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private boolean isLoad=false,firstLoad=true;
    private static final int Holder_type_normal=0;
    private static final int Holder_type_load=1;
    private static final int Holder_type_header=2;
    private LoadingViewHolder loadingViewHolder;
    private OnloadListenr onloadListenr;
    public interface OnloadListenr{
        void onloading();
    }
    public void setOnloadListenr(OnloadListenr onloadListenr) {
        setOnScrollListener();//下拉加载监听
        this.onloadListenr = onloadListenr;
    }

    public BaseAdapter(Context context, List<T> lists, RecyclerView recyclerView) {
        super();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.recyclerView=recyclerView;
        this.lists=lists;
    }
    /**
     * 因为外部第一次时我们直接给的是一个size为0 所有OnCreatView OnBingView并没有执行到 那么就显示不出来
     * 这里我们手动的去让其实现
     */
    private void notifyLoading() {
        if (lists.size() != 0 && lists.get(lists.size() - 1) != null) {
            lists.add(null);
            notifyItemInserted(lists.size() - 1);
        }
    }

    /**
     * 数据加载成功
     */
    public void setLoadingComplete() {
        if (lists.size() > 0 && lists.get(lists.size() - 1) == null) {
            isLoad = false;
            lists.remove(lists.size() - 1);
            notifyItemRemoved(lists.size() - 1);
        }
    }

    /**
     * 加载失败
     */
    public void setLoadingError() {
        if (loadingViewHolder != null) {
            // 加载失败所要呈现的内容
            isLoad = false; //
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("加载失败，请点击屏幕进行重新的加载！");
            loadingViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onloadListenr != null) { // 重写加载的操作
                        isLoad = true;
                        loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                        loadingViewHolder.textView.setText("正在加载 请稍后");
                        onloadListenr.onloading();
                    }
                }
            });
        }
    }

    /**
     * 没有更多的数据了
     */
    public void setLoadingNoMore() {
        isLoad = false;
        if (loadingViewHolder != null) {
            loadingViewHolder.progressBar.setVisibility(View.GONE);
            loadingViewHolder.textView.setText("没有更多的数据！");
//            loadingViewHolder.relativeLayout.setVisibility(View.GONE);
        }
    }

    /**
     * ScrollListener的实现
     */
    private void setOnScrollListener() {
        if (this.recyclerView == null) {
            Log.i("Exception", "并没有设置上recyclerView");
            return;
        }
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滑动状态的更改
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * 滑动的过程当中
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 判定是否是滑到了最后一项 也就是屏幕底端 ViewCompat
                if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
//                    Log.i("onLoading_candown", "ok");
                    if (!isLoad && !firstLoad) {// 不是第一次 也不是加载过程中
//                        Log.i("onLoading", "run");
                        notifyLoading();
                        isLoad = true;
                        if (loadingViewHolder != null) {
                            loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                            loadingViewHolder.textView.setText("正在加载...");
                        }
                        if (onloadListenr != null)
                            onloadListenr.onloading();
                    }
                }
                if (firstLoad)
                    firstLoad = false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        T t=lists.get(position);
        if(t==null){
            return Holder_type_load;
        }else if(position==0){
            return Holder_type_header;
        }else {
            return Holder_type_normal;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     int viewType=getItemViewType(position);
        switch (viewType){
            case Holder_type_load:
                onBindLoadingViewHolder(holder);
                break;
            case Holder_type_header:
                onBindHeaderViewHolder(holder,position);
                break;
            case Holder_type_normal:
                onBindNormalViewHolder(holder,position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        switch (viewType){
            case Holder_type_load:
                viewHolder=onCreateLoadingViewHolder(parent);
                loadingViewHolder = (LoadingViewHolder) viewHolder;
                break;
            case Holder_type_header:
                viewHolder=onCreateHeaderViewHolder(parent);
                break;
            case Holder_type_normal:
                viewHolder=onCreateNormalViewHolder(parent);
                break;
        }
        return viewHolder;
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.loading_progressbar);
            textView=(TextView)itemView.findViewById(R.id.loading_textview);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.loading_relativelayout);
        }
    }
    /**
     * 交给子类去实现的这两个方法
     * 父类只维护到了这边的LoadingViewHolder
     */
    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup);
    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder,int position);

    public abstract RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup viewGroup);
    public abstract void onBindNormalViewHolder(RecyclerView.ViewHolder viewHolder,int position);


    private RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent){
        View itemView=layoutInflater.inflate(R.layout.loading_progressbar,parent,false);
        LoadingViewHolder loadingViewHolder=new LoadingViewHolder(itemView);
        return loadingViewHolder;
    }
    private void onBindLoadingViewHolder (RecyclerView.ViewHolder holder){

    }
    public void addall(List<T> lists){
        if(this.lists!=null)
          this.lists.addAll(lists);
            notifyDataSetChanged();

    }
    public void cleanall(){
        if(this.lists!=null)
            this.lists.clear();
            notifyDataSetChanged();

    }
}
