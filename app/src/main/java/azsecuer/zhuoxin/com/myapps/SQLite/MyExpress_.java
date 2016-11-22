package azsecuer.zhuoxin.com.myapps.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import azsecuer.zhuoxin.com.myapps.Bean.Bean;
import azsecuer.zhuoxin.com.myapps.CollectInfo.Info;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MyExpress_ {
    private SQLiteDatabase db;
    private Context context;
    private Info info=null;
    public MyExpress_(Context context) {
        this.context = context;
        MyDb_ myDb=new MyDb_(context);
        db=myDb.getReadableDatabase();
    }
    //添加数据
    public void addMsg(String title,String iconurl,String time,String type,String url){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("icon",iconurl);
        contentValues.put("time",time);
        contentValues.put("type",type);
        contentValues.put("url",url);
        db.insert("bbb",null,contentValues);
    }
    //查询整列数据
    public List<Info> getMsg(){
        Cursor cursor= db.rawQuery("select * from bbb",null);
        List<Info> list=new ArrayList<>();
       if(cursor.moveToFirst()){
           do{
               info=new Info(cursor.getString(cursor.getColumnIndex("title")),
                       cursor.getString(cursor.getColumnIndex("icon")),
                       cursor.getString(cursor.getColumnIndex("time")),
                       cursor.getString(cursor.getColumnIndex("type")),
                       cursor.getString(cursor.getColumnIndex("url"))
                       );
               list.add(info);
           } while (cursor.moveToNext());
       }

        return list;
    }
    //条件精确查询数据
    public List<Info> getMsg(String name){
        Cursor cursor= db.rawQuery("select * from bbb where url = ?",new String[]{name});
        List<Info> list=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                info=new Info(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("icon")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("url"))
                );
                list.add(info);
            } while (cursor.moveToNext());
        }
        return list;
    }
   //删除数据
    public void remove(String name){
     db.delete("bbb","title=?",new String[]{name});
    }
    //模糊查询数据
    public List<Info> getMsg__(String name){
        Cursor cursor= db.rawQuery("select * from bbb where title like ?",new String[]{'%'+name+'%'});
        List<Info> list=new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                info=new Info(cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("icon")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("url"))
                );
                list.add(info);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
