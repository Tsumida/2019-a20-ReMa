package com.iwktd.rema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelCourse extends SQLiteOpenHelper {
    public final static String tblName = "Course";
    public final static String cid = "cid";
    public final static String cname = "cname"; // text
    public final static String tid = "tid";     // int
    public final static String intro = "intro"; // text
    public final static String likes = "likes"; // int , default 0
    public final static String uid = "uid";  // int

    ModelCourse(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, ModelCourse.tblName, factory, version);
    }

    // 创建数据库， 添加初始数据.
    public void onCreate(SQLiteDatabase db) {
        //String drop = "drop table diary;";
        String createSQL = "create table course(\n" +
                "        cid integer primary key autoincrement,\n" +
                "        cname text,\n" +
                "        tid integer not null,\n" +
                "        intro text,\n" +
                "        likes integer not null,\n" +
                "        uid integer not null);";
        //db.execSQL(drop)
        db.execSQL(createSQL);
        String insert = "insert into course (cid, cname, tid, intro) values \n" +
                "    (1, '计算机安全学', 1, '学习与密码学相关知识，了解密码学历史'),\n" +
                "    (2, '编译原理', 2, '学习如何将代码转换成机器可执行代码的整个过程');";
        db.execSQL(insert);
        Log.d("ModelCourse", "create table.");
    }

    // db版本升级时调用
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    // 每次成功打开db后首先执行
    public void onOpen(SQLiteDatabase db ){
        super.onOpen(db);
        Log.d(ModelCourse.tblName, "Open table "+ ModelCourse.tblName + " ---------- ");
    }

    public static ArrayList<HashMap<String, String>> getAllCourse(Context cnt){
        ArrayList<HashMap<String, String>> res = new ArrayList<>();
        ModelCourse model = new ModelCourse(cnt, null, 1);
        SQLiteDatabase db = model.getReadableDatabase();

        Cursor cursor = db.query(
                ModelCourse.tblName, null,null,null,null,null,ModelCourse.cid, null
        );

        while(cursor.moveToNext()){
            HashMap<String, String> mapper = new HashMap<>();
            mapper.put(ModelCourse.cid, String.valueOf(cursor.getInt(0)));
            mapper.put(ModelCourse.cname, cursor.getString(1));
            mapper.put(ModelCourse.tid, String.valueOf(cursor.getInt(0)));
            mapper.put(ModelCourse.intro, cursor.getString(1));
            mapper.put(ModelCourse.likes, String.valueOf(cursor.getInt(0)));
            mapper.put(ModelCourse.uid, cursor.getString(1));
            res.add(mapper);
        }
        cursor.close();
        db.close();

        return res;
    }

    public HashMap<String, String> getUserByUid(Context cnt, int uid){
        HashMap<String, String> res = new HashMap<>();
        ModelCourse model = new ModelCourse(cnt, null, 1);
        SQLiteDatabase db = model.getReadableDatabase();
        // 只取一个
        Cursor cursor = db.query(
                ModelCourse.tblName, null,null,null,null,null,ModelCourse.uid, "1"
        );
        if (cursor.moveToNext()){
            res.put(ModelCourse.uid, String.valueOf(cursor.getInt(0)));
            res.put(ModelCourse.username, cursor.getString(1));
        }
        cursor.close();
        db.close();
        return res;
    }

}
