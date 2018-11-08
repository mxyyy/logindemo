package net;

import android.os.SystemClock;

import bean.User;

/**
 * date:2018/11/7
 * author:mxy(M)
 * function:
 */
public class UserLoginNet {
    public boolean sendUserLoginInfo(User user){
        //模拟耗时操作
        SystemClock.sleep(2000);
        //对用户输入的信息进行判断
        if("mxy".equals(user.username) && "mxy".equals(user.password)){
            //登录成功
            return true;
        }else{
            //登录失败
            return false;
        }
    }
}
