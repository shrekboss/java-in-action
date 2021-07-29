package org.crayzer.design.design_mode_pattern.structural.adapter.passport;

public class SignForThirdService extends SignService {
    public ResultMsg loginForQQ(String openId){
        //1、openId是全局唯一，我们可以把它当做是一个用户名(加长)
        //2、密码默认为QQ_EMPTY
        //3、注册（在原有系统里面创建一个用户）
        //4、调用原来的登录方法
        return this.loginForRegister(openId, null);
    }

    private ResultMsg loginForRegister(String username, String password) {
        super.regist(username,null);
        return super.login(username,null);
    }
}
