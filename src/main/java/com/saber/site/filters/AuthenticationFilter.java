package com.saber.site.filters;

import com.saber.site.model.UserPrincipal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Principal principal = UserPrincipal.getPrincipal(session);
        if (principal==null){
            response.sendRedirect(request.getContextPath()+"/login");
        }
        else{
            filterChain.doFilter(new HttpServletRequestWrapper(request){
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }
            },response);
        }

    }

    @Override
    public void destroy() {

    }
}
