package azsecuer.zhuoxin.com.myapps.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;
import azsecuer.zhuoxin.com.myapps.R;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress;
import azsecuer.zhuoxin.com.myapps.SQLite.MyExpress_;

public class Webview_Activity extends AppCompatActivity{
    private WebView webView;
    private MyExpress_ myExpress;
    private Toolbar toolbar;
    private TextView textView;
    private ProgressBar progressBar;
    private Snackbar snackbar;
    public String url;
    private boolean needupdate=false;
    private LinearLayout linearLayout;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                progressBar.setProgress(msg.arg1);
                Log.i("msg",msg+"");
                if(msg.arg1==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_);
        linearLayout= (LinearLayout) findViewById(R.id.webview_linearlayout);
        textView= (TextView) findViewById(R.id.webview_textview);
        toolbar= (Toolbar) findViewById(R.id.webview_toolbar);
        progressBar= (ProgressBar) findViewById(R.id.webview_progressbar);
        myExpress=new MyExpress_(this);
        toolbar.setTitle("");
        textView.setText("News");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView= (WebView) findViewById(R.id.webview);
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        webView.loadUrl(url);//加载网页
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Message message=new Message();
                message.what=0;
                message.arg1=newProgress;
                handler.sendMessage(message);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }else {
            finish();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MyExpress_ myExpress_=new MyExpress_(this);
        List<Info> list=myExpress_.getMsg(url);
        if(list.size()==0){
            needupdate=true;
            snackbar.make(linearLayout,"收藏成功",Snackbar.LENGTH_SHORT).show();
        }else {
            snackbar.make(linearLayout,"已经收藏过了哦！",Snackbar.LENGTH_SHORT).show();
            needupdate=false;
        }
        Intent intent=new Intent();
        intent.putExtra("needupdate",needupdate);
        setResult(101,intent);
        return true;
    }

}
