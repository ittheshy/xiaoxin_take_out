package com.yjc.xiaoxin.filter;

import com.alibaba.fastjson.JSON;
import com.yjc.xiaoxin.common.BaseContext;
import com.yjc.xiaoxin.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤没有登录不能访问的请求（登陆优化）
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径比较对象
    public static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获取本次请求的uri
        String requestUri = request.getRequestURI();
        log.info("请求为{}",requestUri);

        //可以放行的请求（白名单）
        String[] uris = new String[]{
             "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login",
                "/user/sendMsg",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        //判断本次请求是否需要处理
        boolean rs = check(uris,requestUri);

        //如果不需要则放行
        if(rs){
            log.info("请求直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        //判断管理端 - 判断是否登录，若已登录则放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("已登录");
            Long id = (Long)request.getSession().getAttribute("employee");
            BaseContext.setId(id);
            filterChain.doFilter(request,response);
            return;
        }
        //判断用户端 - 判断是否登录，若已登录则放行
        if(request.getSession().getAttribute("user") != null){
            log.info("已登录");
            Long id = (Long)request.getSession().getAttribute("user");
            BaseContext.setId(id);
            filterChain.doFilter(request,response);
            return;
        }

        //若未登录则，通过输出流向前端响应对象
        log.info("未登录，返回登录界面");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public static boolean check(String[] uris,String uri){
        for(String url : uris){
            if(PATH_MATCHER.match(url,uri)){
                return true;
            }
        }
        return false;
    }
}
