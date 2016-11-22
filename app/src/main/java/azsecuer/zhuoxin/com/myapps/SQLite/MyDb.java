package azsecuer.zhuoxin.com.myapps.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MyDb extends SQLiteOpenHelper {
    public MyDb(Context context) {
        super(context, "serch.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {//新建表格
        db.execSQL("create table aaa(msg text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
