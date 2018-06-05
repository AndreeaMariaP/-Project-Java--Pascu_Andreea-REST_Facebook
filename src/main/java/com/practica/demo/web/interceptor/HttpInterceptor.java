package com.practica.demo.web.interceptor;

import com.practica.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HttpInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionService sessionService;

   // private static final List<String> WHITELIST_URLS = Arrays.asList("/myUser/login");

    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws java.lang.Exception {
        System.out.println("In http interceptor");
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRemoteUser());
        System.out.println(request.getRemoteAddr());

        // impletemntare securitate
        String requestPath = request.getRequestURI();
        if(WHITELIST_URLS.contains(requestPath)){
            return true;
        }

        if(!sessionService.isLoggedIn()){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        if(requestPath.startsWith("/admin")){
            if(sessionService.isAdmin()){
                return true;
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }

        return true;
    }
*/
}
