package com.saber.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/")
public class MainController {
    @GetMapping
    public View index(){
        return new RedirectView("/ticket/list",true,false);
    }
}
