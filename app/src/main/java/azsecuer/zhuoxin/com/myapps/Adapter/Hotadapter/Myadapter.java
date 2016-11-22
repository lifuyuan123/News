package azsecuer.zhuoxin.com.myapps.Adapter.Hotadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/21.
 */
public class Myadapter extends RecyclerView.Adapter {
    private Context context;
    public List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list=new ArrayList<>();
    private DisplayImageOptions options;
    private boolean first=true;
    public static final int ITEM_HEAD=0;
    public static final int ITEM_NORMAL=1;
    public static final int ITEM_LAST=2;

    public static int LOAD_TYPE=0;

    public List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> getList() {
        return list;
    }

    public void setList(List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list) {
        this.list = list;
    }

    public Myadapter(Context context) {
        this.context=context;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)
                .showImageOnFail(R.drawable.img_news_lodinglose)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
    public void setType(int type){
        LOAD_TYPE=type;
        notifyItemChanged(list.size());
    }
    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    @Override//通过position判断选择什么viewholder
    public int getItemViewType(int position) {
        if(position==0){
            return ITEM_HEAD;
        }else if(position==list.size()){
            return ITEM_LAST;
        }else {
            return ITEM_NORMAL;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_HEAD){
            View view= LayoutInflater.from(context).inflate(R.layout.item_recycle_head,parent,false);
            return new headviewHolder(view);
        }else if(viewType==ITEM_NORMAL){
            View view=LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
            return new normalviewHolder(view);
        }else if (viewType==ITEM_LAST){
            View view=LayoutInflater.from(context).inflate(R.layout.loading_progressbar,parent,false);
            return new lastviewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
          if(holder instanceof headviewHolder){
              Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean=list.get(0);
            ((headviewHolder) holder).header_tv.setText(contentlistBean.getTitle());
              List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageEntity> entity = contentlistBean.getImageurls();
              if (entity.size() != 0)
                  ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                          , ((headviewHolder) holder).header_img, options);
              if(onClick!=null){
                  holder.itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          onClick.click(v,position);
                      }
                  });
              }
          }else if (holder instanceof normalviewHolder){
              Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean=list.get(position);
              ((normalviewHolder) holder).item_news_content.setText(contentlistBean.getSource());
              ((normalviewHolder) holder).item_news_time.setText(contentlistBean.getPubDate());
              ((normalviewHolder) holder).item_news_title.setText(contentlistBean.getTitle());
              List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageEntity> entity = contentlistBean.getImageurls();
              if (entity.size() != 0)
                  ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                          , ((normalviewHolder) holder).item_news_icon, options);
              if(onClick!=null){
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      onClick.click(v,position);
                  }
              });
              }
          }else if(
                  holder instanceof lastviewHolder){
              switch (LOAD_TYPE){
                  case 0:
                      ((lastviewHolder) holder).textView.setText("加载更多");
                      ((lastviewHolder) holder).progressBar.setVisibility(View.GONE);
                      break;
                  case 1:
                      ((lastviewHolder) holder).textView.setText("加载中...");
                      ((lastviewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                      break;
                  case 2:
                      ((lastviewHolder) holder).textView.setText("没有更多数据！");
                      ((lastviewHolder) holder).progressBar.setVisibility(View.GONE);
                      break;
              }
          }
    }


    class headviewHolder extends RecyclerView.ViewHolder{
        public TextView header_tv;
        public ImageView header_img;
        public headviewHolder(View itemView) {
            super(itemView);
            header_tv = (TextView) itemView.findViewById(R.id.item_head_text);
            header_img = (ImageView) itemView.findViewById(R.id.item_head_img);
        }
    }
    class normalviewHolder extends RecyclerView.ViewHolder{
        public TextView item_news_title, item_news_time, item_news_content;
        public ImageView item_news_icon;
        public normalviewHolder(View itemView) {
            super(itemView);
            item_news_title = (TextView) itemView.findViewById(R.id.item_tv_top);
            item_news_time = (TextView) itemView.findViewById(R.id.item_tv_buttomRight);
            item_news_content = (TextView) itemView.findViewById(R.id.item_tv_buttomleft);
            item_news_icon = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
    class lastviewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public TextView textView;
        public lastviewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(R.id.loading_progressbar);
            textView=(TextView)itemView.findViewById(R.id.loading_textview);
        }
    }
    public interface OnClick{
        void click(View view, int position);
    }
    private OnClick onClick;

    public OnClick getOnClick() {
        return onClick;
    }
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public void addall(List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> lists){
        if(this.list!=null)
            this.list.addAll(lists);
        notifyDataSetChanged();
    }
}
