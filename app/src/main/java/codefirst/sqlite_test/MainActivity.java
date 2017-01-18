package codefirst.sqlite_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/***
 * 数据存储----文件存储
 */
public class MainActivity extends AppCompatActivity {

    private EditText save_et;
    private Button skip_sp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skip_sp_btn= (Button) findViewById(R.id.btn_skip_sp);
        skip_sp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sp_intent=new Intent(getApplicationContext(),SharedPreferencesActivity.class);
                startActivity(sp_intent);
            }
        });
        save_et= (EditText) findViewById(R.id.et_save);
        String input_data=load();
        if(!TextUtils.isEmpty(input_data)){         //空值和null都会判TRUE
            save_et.setText(input_data);
            save_et.setSelection(input_data.length());
            Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();

        }
    }
    /***
     * 读入文件数据
     */
    private String load() {
        String line="";     //保存由流转化而来的数据
        FileInputStream input=null;
        BufferedReader reader=null;         //读取流转化成  对象
        StringBuilder content=new StringBuilder();      //读取保存的数据
        try {
            input=openFileInput("fileName");    //读入的文件名
            reader=new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {

            try {
                while ((line=reader.readLine())!=null){         //按行读取
                    content.append(line);       //读取出来存放在StringBuilder对象中
                }
                if(reader!=null){
                    reader.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String input_text=save_et.getText().toString();   //在销毁是保存数据
        save(input_text);
    }
    /***
     * 保存到文件的方法
     * 文件默认存储在/data/data/<package_name>/files/
     */
    public void save(String text) {
        String data=""+text;
        FileOutputStream file=null;
        BufferedWriter writer=null;
        try {
            file=openFileOutput("fileName",MODE_PRIVATE); //文件名，操作模式（若无则创建、同名则覆盖）
            //返回的是一个FileOutputStream对象
            writer=new BufferedWriter(new OutputStreamWriter(file));    //将流最终转化为BufferedWriter对象
            writer.write(data);     //利用方法将文本内容写入文件中
        } catch (IOException e) {
            e.printStackTrace();
        }   finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}