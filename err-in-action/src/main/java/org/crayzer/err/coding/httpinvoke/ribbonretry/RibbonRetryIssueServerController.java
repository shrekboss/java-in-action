package org.crayzer.err.coding.httpinvoke.ribbonretry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("ribbonretryissueserver")
@Slf4j
public class RibbonRetryIssueServerController {
    @GetMapping("wrong")
    public void sendSmsWrong(@RequestParam("mobile") String mobile,
                             @RequestParam("message") String message,
                             HttpServletRequest request) throws InterruptedException {
        log.info("{} is called, {}=>{}", request.getRequestURL().toString(), mobile, message);
        TimeUnit.SECONDS.sleep(2);
    }

    @PostMapping("right")
    public void sendSmsRight(@RequestParam("mobile") String mobile,
                             @RequestParam("message") String message,
                             HttpServletRequest request) throws InterruptedException {
        log.info("{} is called, {}=>{}", request.getRequestURL().toString(), mobile, message);
        TimeUnit.SECONDS.sleep(2);
    }
}
