package com.design.pension_system.sys.util;


import com.design.pension_system.sys.mapper.SignMapper;
import com.design.pension_system.sys.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {


    @Autowired
    private SignMapper signMapper;

    /**
     * 用户如果不登录,则不允许访问服务器.
     * 判断依据:
     * JT_TICKET和JT_USER是否有数据.
     * <p>
     * boolean:
     * true:代表放行
     * false: 当前请求拦截 一般和重定向联用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        String token = request.getHeader("User_Token");

//        //1.获取cookie的值
//        Cookie ticketCookie = CookieUtil.get(request, "TICKET");
//
//        if (ticketCookie == null) {
//            return false; //表示拦截
//        }
//
//        //2.获取数据
//        String ticket = ticketCookie.getValue();

        if (StringUtils.isEmpty(token)) {
            return false; //表示拦截
        }

        String ticket = DigestUtils.md5DigestAsHex(token.getBytes());

        String falg = signMapper.checkToken(ticket);
        if (null == falg) {
            return false;
        }

        //5.表示用户信息正确的,应该放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

