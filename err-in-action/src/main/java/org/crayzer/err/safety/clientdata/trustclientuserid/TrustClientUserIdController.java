package org.crayzer.err.safety.clientdata.trustclientuserid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("trustclientuserid")
@RestController
public class TrustClientUserIdController {
    // 用户登录，业务代码非常容易犯错的一个地方是，使用了客户端传给服务端的用户 ID，类似这样：
    @GetMapping("wrong")
    public String wrong(@RequestParam("userId") Long userId) {
        return "当前用户Id：" + userId;
    }

    @GetMapping("right")
    public String right(@LoginRequired Long userId) {
        return "当前用户Id：" + userId;
    }

    // http://localhost:45678/trustclientuserid/login?username=admin&password=admin
    @GetMapping("login")
    public long login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (username.equals("admin") && password.equals("admin")) {
            session.setAttribute("currentUser", 1L);
            return 1L;
        }
        return 0L;
    }
}
