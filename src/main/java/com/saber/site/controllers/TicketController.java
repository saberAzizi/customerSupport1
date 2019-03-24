package com.saber.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    @GetMapping(value = "list")
    @ResponseBody
    public String list(Map<String,Object> model){
        return "<h2>Welcome</h2>";
    }
}
