package org.crayzer.err.safety.clientdata.trustclientip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

@Slf4j
@RequestMapping("trustclientip")
@RestController
public class TrustClientIpController {

    HashSet<String> activityLimit = new HashSet<>();

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        String ip = getClientIp(request);
        if (activityLimit.contains(ip)) {
            return "您已经领取过奖品";
        } else {
            activityLimit.add(ip);
            return "奖品领取成功";
        }
    }

    // IP 地址的获取方式是：优先通过 X-Forwarded-For 请求头来获取，
    // 如果没有的话再通过 HttpServletRequest 的 getRemoteAddr 方法来获取。

    // 之所以这么做是因为，通常我们的应用之前都部署了反向代理或负载均衡器，
    // remoteAddr 获得的只能是代理的 IP 地址，而不是访问用户实际的 IP。这不符合需求，
    // 因为反向代理在转发请求时，通常会把用户真实 IP 放入 X-Forwarded-For 这个请求头中。

    // 这种过于依赖 X-Forwarded-For 请求头来判断用户唯一性的实现方式，是有问题的：
    // curl http://localhost:45678/trustclientip/test -H "X-Forwarded-For:183.84.18.71, 10.253.15.1"

    // 因此，IP 地址或者说请求头里的任何信息，包括 Cookie 中的信息、Referer，只能用作参考，不能用
    // 作重要逻辑判断的依据。而对于类似这个案例唯一性的判断需求，更好的做法是，让用户进行登录或三
    // 方授权登录（比如微信），拿到用户标识来做唯一性判断。
    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff == null) {
            return request.getRemoteAddr();
        } else {
            return xff.contains(",") ? xff.split(",")[0] : xff;
        }
    }
}
