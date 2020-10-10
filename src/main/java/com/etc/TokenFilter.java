package com.etc;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);


    @Override
    public String filterType() {//表示过滤器的类型,pre表示路由前执行
        return "pre";
    }

    @Override
    public int filterOrder() {//过滤器的执行顺序，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {//过滤器的执行条件，为true表示过滤器执行，false表示不执行
        return true;
    }

    @Override
    public Object run() throws ZuulException {//过滤器具体要执行的代码
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("--->>> TokenFilter {},{}",request.getMethod(),request.getRequestURL().toString());
        String token = request.getParameter("token");//获取 token请求的参数
        if(token != null && !"".equals(token)){
            ctx.setSendZuulResponse(true);//允许路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess",true);
        }else{//不允许路由
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("toke is empty!");
            ctx.set("isSuccess",false);
        }
        return null;
    }
}
