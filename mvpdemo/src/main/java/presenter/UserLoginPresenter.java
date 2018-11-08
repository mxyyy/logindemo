package presenter;

import android.text.TextUtils;

import net.UserLoginNet;

import bean.User;

/**
 * date:2018/11/7
 * author:mxy(M)
 * function:
 */
public class UserLoginPresenter {
    public UserLoginPresenter(UserLoginPresenterInterface userLoginPresenterInterface) {
        mUserLoginPresenterInterface = userLoginPresenterInterface;
    }

    /*对用户输入进行非空判断，业务逻辑*/
    public boolean submit(User user){
        if(TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)){
            return false;
    }
        return true;
    }
    public void login(final User user){
        //2.开一个子线程做耗时操作，业务逻辑
        new Thread(){
            public void run(){
                //3.创建网络工具类对象，业务逻辑
                UserLoginNet net = new UserLoginNet();
                //4.对用户输入的信息进行判断。。业务逻辑
                if(net.sendUserLoginInfo(user)){
                    //5.Activity对象调用更新视图的方法
                    mUserLoginPresenterInterface.success();
                }else{
                    mUserLoginPresenterInterface.failed();
                }
            }
        }.start();
    }
    /**
     * 定义接口,无论是Activity还是Fragment都可以使用Presenter的业务逻辑
     * 提示:接口里的代码注释必须写(分配任务:高级程序员,负责写Presenter层,写业务逻辑,定义接口,中级程序员负责写VIew层,更新数据用接口)
     */
    public interface UserLoginPresenterInterface{
        /*登陆成功*/
        void success();
        /*登录失败*/
        void failed();
    }
    private UserLoginPresenterInterface mUserLoginPresenterInterface;

}
