package com.cja.example.controller;

import com.example.currentlimitstarter.annotation.CurrentLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/test")
    @CurrentLimiter(QPS = 10, timeOut = 3L)
    public String test() throws InterruptedException {
        Thread.sleep(2000);
        return "执行业务成功";
    }

}
