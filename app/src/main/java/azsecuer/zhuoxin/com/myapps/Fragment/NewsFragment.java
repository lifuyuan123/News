package azsecuer.zhuoxin.com.myapps.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Activity.AddorMoveTitleActivity;
import azsecuer.zhuoxin.com.myapps.Adapter.NewfragmentpagarAdapter;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.Util.SharedpreferencesUtil;

/**
 * Created by Administrator on 2016/9/1.
 */
public class NewsFragment extends Fragment implements View.OnClickListener{
    private ImageView imageView;
    private NewfragmentpagarAdapter adapter;
    private List<Fragment> fragments;
    private List<String> titles=new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedpreferencesUtil sharedpreferencesUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=(ViewPager)view.findViewById(R.id.news_viewpaper);
        tabLayout=(TabLayout)view.findViewById(R.id.tablayout);
        imageView=(ImageView)view.findViewById(R.id.im_news);
        initViewpagerFragment();
        imageView.setOnClickListener(this);
    }

    private void initViewpagerFragment() {
        fragments=new ArrayList<>();
        if (titles != null)
            titles.clear();
        fragments.clear();
        sharedpreferencesUtil=new SharedpreferencesUtil(getActivity());
        titles= sharedpreferencesUtil.getSharedptodatalist();
        for(int i=0;i<titles.size();i++){
            TestFragment testFragment=new TestFragment();
            //将页卡标题传递过去
            Bundle bundle=new Bundle();
            bundle.putString("newname",titles.get(i));
            testFragment.setArguments(bundle);
            fragments.add(testFragment);
        }
        adapter=new NewfragmentpagarAdapter(getActivity().getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.im_news :
            Intent intent=new Intent(getActivity(), AddorMoveTitleActivity.class);
            startActivityForResult(intent,100);
        break;
    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==101&&data.getBooleanExtra("needupdate",false)){
        initViewpagerFragment();
        }
    }
}
