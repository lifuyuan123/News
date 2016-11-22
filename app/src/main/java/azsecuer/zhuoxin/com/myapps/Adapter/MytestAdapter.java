package azsecuer.zhuoxin.com.myapps.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.Bean.VolleyInfo;
import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.Manager.URLManager;
import azsecuer.zhuoxin.com.myapps.R;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MytestAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Info> lists;
    private DisplayImageOptions options;
//    private List<VolleyInfo.NewslistBean> lists;
    public MytestAdapter(Context context,List<Info> lists) {
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.lists=lists;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.test_item,null);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.iv_item);
            viewHolder.textViewtop=(TextView)convertView.findViewById(R.id.item_tv_top);
            viewHolder.text_no=(TextView)convertView.findViewById(R.id.text_no);
            viewHolder.textViewleft=(TextView)convertView.findViewById(R.id.item_tv_buttomleft);
            viewHolder.textViewright=(TextView)convertView.findViewById(R.id.item_tv_buttomRight);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Info contentlistBean=lists.get(position);
//        URLManager.getInstence().ImagLoader(contentlistBean.getImageurls().get(0).getUrl(),viewHolder.imageView);
//        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        ImageLoader.getInstance().displayImage(contentlistBean.iconurl
                , viewHolder.imageView, options);
//        VolleyInfo.NewslistBean contentlistBean=lists.get(position);
//        URLManager.getInstence().ImagLoader(contentlistBean.getPicUrl(),viewHolder.imageView);
        viewHolder.textViewtop.setText(contentlistBean.title);
        viewHolder.textViewleft.setText(contentlistBean.type);
        viewHolder.textViewright.setText(contentlistBean.time);
        viewHolder.text_no.setText("取消收藏");
        viewHolder.text_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textnoOclick.callback(v,position);
            }
        });
//        viewHolder.textViewleft.setText(contentlistBean.getCtime());
//        viewHolder.textViewright.setText(contentlistBean.getDescription());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textViewtop,textViewleft,textViewright,text_no;
    }
    public interface TextnoOclick{
        void callback(View v,int position);
    }
    public TextnoOclick textnoOclick;

    public TextnoOclick getTextnoOclick() {
        return textnoOclick;
    }

    public void setTextnoOclick(TextnoOclick textnoOclick) {
        this.textnoOclick = textnoOclick;
    }
}
