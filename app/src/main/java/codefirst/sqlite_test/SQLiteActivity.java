package codefirst.sqlite_test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by cheng on 2017/1/16.
 */

public class SQLiteActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private Button create_db_btn;       //创建
    private Button add_btn;
    private Button updata_btn;
    private Button delete_btn;
    private Button query_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_layout);
                                                         //版本号改变
        dbHelper=new MyDatabaseHelper(getApplicationContext(),"BookStore.db",null,2);   //创建实例，使用构造方法
        init();
        initClick();

    }

    //监听事件
    private void initClick() {

        create_db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();         //调用实例方法，即可创建DB
                Toast.makeText(getApplicationContext(),"创建成功",Toast.LENGTH_LONG).show();
            }
        });

        //添加数据
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();       //获取SQLiteDatabase对象
                ContentValues values=new ContentValues();   //使用CV对数据组装
                values.put("author","cheng");
                values.put("price","23.3");
                db.insert("book",null,values);          //插入第一条数据
                values.clear();         //清空
                values.put("id",03);
                values.put("author","white");
                values.put("price","14.3");
                db.insert("category",null,values);          //插入
                values.clear();         //清空
                values.put("id",06);
                values.put("author","cheng_cate");
                values.put("price","33.33");
                db.insert("category",null,values);          //插入
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_LONG).show();
            }
        });

        updata_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("author","black");           //需要更新的数据
                db.update("book",values,"author=?",new String[]{"cheng"});
                                        //约束更新的列名         提供第三个参数的候选项
                Toast.makeText(getApplicationContext(),"更新成功",Toast.LENGTH_LONG).show();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.delete("categoty","price>?",new String[]{"40"});
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
            }
        });
        //返回一个Cursor对象----查询
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("category",null,null,null,null,null,null);   //查询的约束条件
                if(cursor.moveToFirst()){   //控制指针
                    do{
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int price=cursor.getInt(cursor.getColumnIndex("price"));
                        int id=cursor.getInt(cursor.getColumnIndex("id"));
                        Log.d("tag","author"+author+" price"+price+" id"+id);
                    }while (cursor.moveToNext());   //游标移动

                }
                cursor.close(); //关闭
                Toast.makeText(getApplicationContext(),"查询成功",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        create_db_btn= (Button) findViewById(R.id.btn_create);
        add_btn= (Button) findViewById(R.id.btn_add);
        updata_btn= (Button) findViewById(R.id.btn_update);
        delete_btn= (Button) findViewById(R.id.btn_delete);
        query_btn= (Button) findViewById(R.id.btn_query);
    }
}