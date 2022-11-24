package com.example.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RestController
public class CallableController {

    @GetMapping("/callable")
    public Callable<String> callableView() throws InterruptedException {
        log.info("callable");
        return () -> {
            log.info("async");
            Thread.sleep(3000);
            return "Hello";
        };
    }
}
