package codefirst.sqlite_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/***
 * SharedPerferences数据存储
 */
public class SharedPreferencesActivity extends AppCompatActivity {

    private Button login_btn;
    private CheckBox remember_cb;
    private EditText account_et;
    private EditText password_et;
    private SharedPreferences.Editor account_Editor;
    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_prefer_layout);

        init();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember_password",false);     //将用户选中的状态数据
        if(isRemember){       //获取已经保存的数据
            String account=pref.getString("account","");        //用pref取出数据
            String password=pref.getString("password","");
            account_et.setText(account);
            password_et.setText(password);
            remember_cb.setChecked(true);
            Log.d("tag3", account + " " + password);
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = account_et.getText().toString();       //根据输入的编辑框中的数据
                String password = password_et.getText().toString();
                boolean remember_pas=remember_cb.isChecked();            //是否记住密码
                if (account.equals("ed") && password.equals("23")) {
                    account_Editor = pref.edit();  //创建对象           用editor存数据

                    if(remember_pas){       //如果记住密码，则存储数据
                        account_Editor.putString("account", account);        //将保存在变量中的数据存储到SP中
                        account_Editor.putString("password", password);
                        account_Editor.putBoolean("remember_password",remember_pas);          //包括记住密码的状态数据
                    }else {
                        account_Editor.clear();         //清除数据
                    }
                    account_Editor.commit();

                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    Intent Sql_intent = new Intent(getApplicationContext(), SQLiteActivity.class);
                    startActivity(Sql_intent);
                    //finish();
                } else {
                    Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //初始化控件
    private void init() {
        login_btn = (Button) findViewById(R.id.btn_login);
        remember_cb = (CheckBox) findViewById(R.id.cb_remember);
        account_et = (EditText) findViewById(R.id.et_account);
        password_et = (EditText) findViewById(R.id.et_password);
    }
}
