package com.saber.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/errorHandler")
public class ErrorController {
    @GetMapping
    public String error(Map<String, Object> model, HttpServletRequest request) {
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        model.put("servletName", servletName);
        model.put("statusCode", statusCode);
        model.put("throwable", throwable);
        model.put("requestUri", requestUri);
        return "error";
    }

}
