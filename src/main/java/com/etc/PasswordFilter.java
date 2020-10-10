package com.etc;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class PasswordFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(PasswordFilter.class);

    @Override
    public String filterType() {//路由之后过滤
        return "post";
    }

    @Override
    public int filterOrder() {//执行的顺序
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        boolean isSuccess = (boolean)ctx.get("isSuccess");//从上一个过滤器中获取isSuccess的值
        return isSuccess;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("--->>>PasswordFilter {},{}",request.getMethod(),request.getRequestURL().toString());
        String password = request.getParameter("password");
        if(password != null && "123456".equals(password)){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess",true);
        }else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.set("isSuccess",false);
            ctx.setResponseBody("The password is not empty and password must is right");
        }
        return null;
    }
}
