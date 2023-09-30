package com.numberone.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class HelloController {
    @RequestMapping("")
    public String hello(){
        log.info("[hello controller is called]");
        return "Hello, NumberOne";
    }
    @RequestMapping("/test")
    public String test(){
        log.info("[test controller is called]");
        return "Test, NumberOne";
    }
    @RequestMapping("/final-test")
    public String finalTest(){
        log.info("[test controller is called]");
        return "Final Test, NumberOne";
    }
}
