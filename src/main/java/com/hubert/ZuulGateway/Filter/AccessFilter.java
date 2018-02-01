package com.hubert.ZuulGateway.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        /**
         * pre：可以在请求被路由之前调用
         * routing：在路由请求时候被调用
         * post：在routing和error过滤器之后被调用
         * error：处理请求时发生错误时被调用
         */
        return "pre";
    }

    /**
     * 通过int值来定义过滤器的执行顺序,值越大，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        //http://localhost:5555/api-a/add?a=1&b=2&accessToken=token access
        //http://localhost:5555/api-a/add?a=1&b=2   cannot access
        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);//令zuul过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);//设置了其返回的错误码
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
