package com.etc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/index")
    public ModelAndView index(String url){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("url",url);
        return mv;
    }

    @RequestMapping("/sysindex")
    public ModelAndView sysindex(){
        ModelAndView mv = new ModelAndView("sysindex");
        return mv;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String uname, String password, String url, HttpServletResponse response, HttpSession session) throws IOException {
        logger.info("User Name:{},Password:{}",uname,password);
        if("admin".equals(uname)&&"xmetc".equals(password)) {
            session.setAttribute("user", uname);
            if(url != null && !"".equals(url)){//从访问的url地址过来的登陆，登陆成功后，回到原url
                response.sendRedirect(url);
            }else{//url为空则登陆成功后去后台首页
                response.sendRedirect("/sysindex");
            }
        }
        return "用户登陆成功！";
    }
}
