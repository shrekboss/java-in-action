package org.crayzer.spring.bean.scope.web.controller;

import org.crayzer.spring.ioc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.AbstractRequestAttributesScope;

/**
 * 首页 Spring Web MVC Controller
 *
 * @author Crayzer
 */
@Controller
public class IndexController {

    @Autowired
    private User user; // CGLIB 代理后对象（不变的）

    /**
     * {@link AbstractRequestAttributesScope} get()
     *
     * @param model
     * @return
     */
    @GetMapping("/index.html")
    public String index(Model model) {
        // JSP EL 变量搜索路径 page -> request -> session -> application(ServletContext)
        // userObject -> 渲染上下文
        // user 对象存在 ServletContext，上下文名称：scopedTarget.user == 新生成 Bean 名称
        model.addAttribute("userObject", user);
        return "index";
    }
}
