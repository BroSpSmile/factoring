package com.smile.start.filter;

import com.smile.start.commons.Constants;
import com.smile.start.service.UserInfoService;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Joseph
 * @version v1.0 2019/1/24 19:52, LoginFilter.java
 * @since 1.8
 */
@Order(1)
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/login", "/logout", "/js", "/css", "/image", "/favicon.ico")));

    @Resource
    private UserInfoService userInfoService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        if(isAllowed(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = getToken(request);
            if(token != null && userInfoService.validateToken(token)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
        }
    }

    private boolean isAllowed(String path) {
        for(String allowed : ALLOWED_PATHS) {
            if(path.startsWith(allowed)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies =  request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.TOKEN_COOKIE_KEY)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
