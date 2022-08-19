package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    // For Healthy Check
    @GetMapping("/")
    public String home(){
        return "connection";
    }

    @GetMapping("/token/test")
    public String tokenTest(HttpServletRequest request){
        return getUsername(request);
    }

    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }
}
