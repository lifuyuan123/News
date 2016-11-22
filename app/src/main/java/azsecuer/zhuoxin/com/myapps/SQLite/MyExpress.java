package azsecuer.zhuoxin.com.myapps.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MyExpress {
    private SQLiteDatabase db;
    private Context c;
    public MyExpress(Context context) {
        this.c = context;
        MyDb myDb=new MyDb(c);
        db=myDb.getReadableDatabase();
    }
    //添加数据
    public void addMsg(String s){
        ContentValues contentValues=new ContentValues();
        contentValues.put("msg",s);
        db.insert("aaa",null,contentValues);
    }
    //查询数据
    public List<String> getMsg(String name){
        Cursor cursor= db.rawQuery("select * from aaa where msg like ?",new String[]{'%'+name+'%'});
        List<String> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String s=cursor.getString(cursor.getColumnIndex("msg"));
            list.add(s);
        }
        return list;
    }
}
