package com.etc;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public String filterType() {//路由之前执行的过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        logger.info("session.getAttribute('user'):"+user);
        String url = request.getRequestURL().toString();//获取请求的url地址
        logger.info("--->>>LoginFilter:{}{}",request.getMethod(),url);
        if(user != null){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess",true);
        }else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.getResponse().setCharacterEncoding("utf-8");
            ctx.getResponse().setContentType("text/html");
            ctx.setResponseBody("<a href='/index?url="+url+"'>请先登陆</a>");
            ctx.set("isSuccess",false);
        }
        return null;
    }
}
