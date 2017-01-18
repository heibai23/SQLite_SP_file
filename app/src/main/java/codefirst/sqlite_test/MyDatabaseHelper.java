package codefirst.sqlite_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/***
 *  创建数据库的辅助类
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    //创建数据库的书写标准
    public static final String CREATE_BOOK="create table book( " +
            "id integar primary key,"+"author text,"+"price real)";
    public static final String CREATE_CATEGORY="create table category(" +
            "id integar primary key ,author text,age integar ,pages integar，price real)";
                                //不能有自动增长autoincrement,报错，原因不详
    private Context mContext;       //上下文，在不能用方法时可在其他方法中使用

    /***
     * 构造方法
     * @param context
     * @param name   数据库名称
     * @param factory       查询数据时返回一个自定义的Cursor，一般为null
     * @param version   版本号
     */
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;

    }

    //数据库创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);    //执行建表语句
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext,"创建表成功",Toast.LENGTH_LONG).show();
    }

    /***数据库升级
     * @param db  数据库类
     * @param oldVersion        旧版本数据库号
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion , int newVersion) {
        db.execSQL("drop table if exists BOOK");
        db.execSQL("drop table if exists CATEGORY");    //升级，要先将已有的表删掉，因为数据库已经存在，不会再次执行哦你create方法
        onCreate(db);       //执行创建方法
    }
}
