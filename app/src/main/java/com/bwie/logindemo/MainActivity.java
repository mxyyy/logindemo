package com.bwie.logindemo;

import android.app.ProgressDialog;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button login;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
    }

    private void initView() {
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(this);

        //动态的创建一个进度条
        mProgressDialog = new ProgressDialog(this);
    }


    //根据点击事件模拟登录的逻辑
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                final String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                //创建Bean类,然后把数据设置给Bean类
                final User user = new User();
                user.username =username;
                user.password =password;
                //判断信息是否为null,|,||,&&,&
                if (TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)){
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //显示进度条
                mProgressDialog.show();
                //模拟网络请求
                new Thread(){
                    public void run() {
                        //模拟耗时操作
                        SystemClock.sleep(2000);
                        //关闭进度条
                        mProgressDialog.dismiss();
                        //对用户输入的信息进行判断
                        if ("ycf".equals(user.username) && "ycf".equals(user.password)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "欢迎回来,思密达", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }//账号秘密有问题,登录失败
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "登录失败,账号密码有问题", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    };
                }.start();
                break;
        }
    }

}
