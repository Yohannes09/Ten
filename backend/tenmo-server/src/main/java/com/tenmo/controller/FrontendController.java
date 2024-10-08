package com.tenmo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@Controller
public class FrontendController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String signup(){
        return "signup";
    }

    @GetMapping("success")
    public String success(){
        return "success";
    }
}
