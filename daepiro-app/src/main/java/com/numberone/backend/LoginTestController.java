package com.numberone.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginTestController {
    @GetMapping("/api/logintest")
    public LoginTestResponse test() {
        return LoginTestResponse.of("test");
    }
}
