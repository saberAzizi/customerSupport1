package com.saber.config;

import com.saber.site.filters.AuthenticationFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.*;

@SuppressWarnings("ALL")
public class BootStrap implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContainer) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfiguration.class);
        servletContainer.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext webServletContext = new AnnotationConfigWebApplicationContext();
        webServletContext.register(WebServletContextConfiguration.class);

        ServletRegistration.Dynamic dispatcherServlet = servletContainer.addServlet("SpringDispptcherServlet",
                new DispatcherServlet(webServletContext));

        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setMultipartConfig(new MultipartConfigElement(null,30_456_986L,
                38_985_986L,8_895));

        FilterRegistration filterRegistration = servletContainer.addFilter("authenticationFilter",new AuthenticationFilter());

        filterRegistration.addMappingForServletNames(null,false,"/ticket","/ticket/*");
    }
}
