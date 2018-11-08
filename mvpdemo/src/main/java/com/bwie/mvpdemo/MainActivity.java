package com.bwie.mvpdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bean.User;
import presenter.UserLoginPresenter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, UserLoginPresenter.UserLoginPresenterInterface {

    //视图层
    private EditText mUsername;
    private EditText mPassword;
    private Button login;
    private ProgressDialog mProgressDialog;
    private UserLoginPresenter mUserLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行初始化控件
        initView();
        //动态的创建一个进度条，视图层
        mProgressDialog = new ProgressDialog(this);
        //创建presenter类对象，把Activity的业务逻辑的类对象拆分出去，把Activity对象传进去
        mUserLoginPresenter = new UserLoginPresenter(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                //视图层
                final String  username= mUsername.getText().toString().trim();
                final String  password= mPassword.getText().toString().trim();
                //创建Bean类,然后把数据放到bean类里,视图层
                final User user = new User();
                user.username=username;
                user.password=password;

                //判断信息是否为null,业务逻辑层
                boolean userInfo = mUserLoginPresenter.submit(user);
                //也是业务逻辑.****
                if (userInfo){
                    //显示进度条,视图层
                    mProgressDialog.show();
                    //1.使用presenter,这个处理业务逻辑的类,对用户输入信息是否正确进行判断
                    mUserLoginPresenter.login(user);
                }else {
                    //视图层
                    Toast.makeText(MainActivity.this, "输入不能为null,么么哒", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void success() {
        //5.登录成功的逻辑,谈一个吐司,更新UI       视图层
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //7.关闭进度条,弹吐司
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "欢迎回来,思密达", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void failed() {
        //登录失败的逻辑,谈一个吐司,更新UI    视图层
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //关闭进度条
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "您的信息有误,请重新填写", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //初始化控件     视图层
    private void initView() {
        mUsername =  findViewById(R.id.username);
        mPassword =  findViewById(R.id.password);
        login =  findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserLoginPresenter=null;
    }
}
