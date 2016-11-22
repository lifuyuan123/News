package azsecuer.zhuoxin.com.myapps.Adapter.baseAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.R;


/**
 * @author: leejohngoodgame
 * @date: 2016/9/16 17:26
 * @email:18328541378@163.com
 */
public class NewsContentAdapter extends BaseAdapter<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> {
    //    private Context context;
    public List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> myDatas;
    private DisplayImageOptions options;
    public MyRecyclerListener listener;
    public interface MyRecyclerListener{
        void onMyRecyclerClickd(View view, int position);
    }
    public MyRecyclerListener getListener() {
        return listener;
    }
    public void setListener(MyRecyclerListener listener) {
        this.listener = listener;
    }

    public NewsContentAdapter(List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> myDatas, Context context, RecyclerView recyclerView) {
        super(context,myDatas,recyclerView);
        this.myDatas = myDatas;
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.img_news_lodinglose)
                .showImageOnFail(R.drawable.img_news_lodinglose)
                .showImageOnLoading(R.drawable.img_news_loding)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View itemView = super.layoutInflater.inflate(R.layout.item_recycle_head, viewGroup, false);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(itemView);
        return headerViewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        final HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
        headerViewHolder.header_tv.setText(myDatas.get(position).getTitle());
//        headerViewHolder.header_tv.setBackgroundResource(R.drawable.icon_aboutme);
        List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageEntity> entity = myDatas.get(position).getImageurls();
        if (entity.size() != 0)
            ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                    , headerViewHolder.header_img, options);
        if(listener!=null){
            headerViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     listener.onMyRecyclerClickd(headerViewHolder.relativeLayout,position);
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalViewHolder(ViewGroup viewGroup) {
        NormalViewHolder normalViewHolder = new NormalViewHolder(layoutInflater.inflate(R.layout.news_item, viewGroup, false));
        return normalViewHolder;
    }

    @Override
    public void onBindNormalViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        final NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
        Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = (Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean) myDatas.get(position);
        normalViewHolder.item_news_content.setText(contentlistBean.getSource());
//        normalViewHolder.item_news_icon.setBackgroundResource(R.drawable.icon_aboutme);
        normalViewHolder.item_news_time.setText(contentlistBean.getPubDate());
        normalViewHolder.item_news_title.setText(contentlistBean.getTitle());
        List<Bean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.ImageEntity> entity = myDatas.get(position).getImageurls();
        if (entity.size() != 0)
            ImageLoader.getInstance().displayImage(entity.get(0).getUrl()
                    , normalViewHolder.item_news_icon, options);
        if(listener!=null){
            normalViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyRecyclerClickd(normalViewHolder.linearLayout,position);
                }
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_tv;
        public ImageView header_img;
        public RelativeLayout relativeLayout;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            header_tv = (TextView) itemView.findViewById(R.id.item_head_text);
            header_img = (ImageView) itemView.findViewById(R.id.item_head_img);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.head_relativelayout);
        }
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView item_news_title, item_news_time, item_news_content;
        public ImageView item_news_icon;
        public LinearLayout linearLayout;

        public NormalViewHolder(View itemView) {
            super(itemView);
            item_news_title = (TextView) itemView.findViewById(R.id.item_tv_top);
            item_news_time = (TextView) itemView.findViewById(R.id.item_tv_buttomRight);
            item_news_content = (TextView) itemView.findViewById(R.id.item_tv_buttomleft);
            item_news_icon = (ImageView) itemView.findViewById(R.id.iv_item);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.normal_lineatlayout);
        }
    }
}
