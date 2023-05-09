package com.security.test.securityconftest.login;

import com.security.test.securityconftest.config.security.JWTService;
import com.security.test.securityconftest.login.data.form.LoginRequest;
import com.security.test.securityconftest.member.data.repo.MemberRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginConrtoller {

    @Autowired
    private MemberRepositroy memberRepositroy;

    @Autowired
    private  JWTService jwtService;

    @GetMapping("/login")
    public String showLoginPage() {
        System.err.println("拿到登入畫面");
        return "login";
    }

    @PostMapping("/selflogin")
    public String signOrLogin(@RequestParam (name = "username") String username, @RequestParam (name = "password") String password, HttpServletResponse response){
        System.err.println("處理帳號密碼");
        memberRepositroy.findByUsername(username)
                .map(v -> jwtService.generateJWT(v.getUsername(), 60L * 60L * 1000L, UUID.randomUUID()+""))
//                .map(v -> ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", v)))
                .map( v -> {
                    response.setHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %S", v));
                    return v;
                })
                .orElseThrow(()-> new RuntimeException("signOrLogin err"));
        System.err.println(response.getHeader(HttpHeaders.AUTHORIZATION));
        return "myindex";
    }

    @GetMapping("/need")
    public String getNeed(){
        return "need";
    }
}
