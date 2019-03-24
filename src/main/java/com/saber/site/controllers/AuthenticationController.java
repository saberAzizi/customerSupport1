package com.saber.site.controllers;

import com.saber.site.model.LoginForm;
import com.saber.site.model.RegisterForm;
import com.saber.site.model.UserPrincipal;
import com.saber.site.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @GetMapping(value = "logout")
    public View logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/ticket/list",true,false);
    }
    @GetMapping(value = "login")
    public String login(Map<String,Object> model){
        model.put("loginForm",new LoginForm());
        model.put("loginFailed",false);
        return "login";
    }
    @GetMapping(value = "register")
    public String register(Map<String,Object>model){
        model.put("registerForm",new RegisterForm());
        return "register";
    }
    @PostMapping(value = "login")
    public ModelAndView login(Map<String,Object> model, @Valid LoginForm loginForm,
                              Errors errors, HttpSession session, HttpServletRequest request){
        if (errors.hasErrors()){
            model.put("errors",errors.getAllErrors());
            loginForm.setPassword(null);
            model.put("loginForm",loginForm);
            model.put("loginFailed",false);
            return new ModelAndView("login");
        }
        UserPrincipal principal = this.authenticationService
                .authenticate(loginForm.getUsername(), loginForm.getPassword());
        if (principal==null){
            model.put("loginFailed",true);
            loginForm.setPassword(null);
            model.put("loginForm",loginForm);
            return new ModelAndView("login");
        }
        UserPrincipal.setPrincipal(session,principal);
        request.changeSessionId();
        return new ModelAndView(new RedirectView("/ticket/list",true,false));
    }
    @PostMapping(value = "register")
    public ModelAndView register(Map<String,Object> model,@Valid RegisterForm registerForm,Errors errors){
        if (errors.hasErrors()){
            model.put("errors",errors.getAllErrors());
            registerForm.setPassword(null);
            model.put("registerForm",registerForm);
            return new ModelAndView("register");
        }
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(registerForm.getUsername());
        userPrincipal.setPassword(registerForm.getPassword());
        this.authenticationService.saveUser(userPrincipal);
        return new ModelAndView(new RedirectView("successRegister",true,false));
    }
    @GetMapping(value = "successRegister")
    public String successRegister(Map<String,Object>model){
        model.put("successRegister",true);
        return this.login(model);
    }
}
