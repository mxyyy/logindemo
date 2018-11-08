package com.bwie.mvc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.UserLoginNet;

import bean.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //界面相关V
    private EditText mUsername;
    private EditText mPassword;
    private Button login;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //进行控件初始化   界面相关 V
        initView();
        //动态的创建一个进度条    界面相关 V
        mProgressDialog = new ProgressDialog(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                //界面相关 V
                final String  username= mUsername.getText().toString().trim();
                final String  password= mPassword.getText().toString().trim();
                //创建Bean类,然后把数据放到bean类里
                final User user = new User();
                user.username=username;
                user.password=password;

                //判断信息是否为null   业务相关 C
                boolean userInfo = submit(user);
                if (userInfo){
                    //显示进度条     界面相关 V
                    mProgressDialog.show();
                    //开一个子线程做耗时操作   业务相关 C
                    new Thread(){
                        public void run() {
                            //创建网络工具类对象
                            UserLoginNet net = new UserLoginNet();
                            //对用户输入的信息进行判断
                            if(net.sendUserLoginInfo(user)){
                                //登录成功的逻辑,谈一个吐司,更新UI
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //关闭进度条         界面相关 V
                                        mProgressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "欢迎回来,思密达", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                //登录失败的逻辑,谈一个吐司,更新UI     界面相关 V
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //关闭进度条
                                        mProgressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "您的信息有误,请重新填写", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        };
                    }.start();

                }else {
                    //界面相关 V
                    Toast.makeText(MainActivity.this, "输入不能为null,么么哒", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private boolean submit(User user) {
        if (TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)) {
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void initView() {
        mUsername =  findViewById(R.id.username);
        mPassword =  findViewById(R.id.password);
        login =  findViewById(R.id.login);
        login.setOnClickListener(this);
    }
}
