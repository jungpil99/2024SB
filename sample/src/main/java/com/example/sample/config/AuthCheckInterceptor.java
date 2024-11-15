package com.example.sample.config;

import com.example.sample.spring.AuthInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if(session != null) {
            AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
            if(authInfo != null) {
                request.setAttribute("isAdmin", "Admin".equals(authInfo.getRole()));
                return true;
            }
        }

        response.sendRedirect("/login");
        return false;
    }
}
