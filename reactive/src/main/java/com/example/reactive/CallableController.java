package com.example.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@RestController
public class CallableController {

    @GetMapping("/callable")
    public Callable<String> callableView() throws InterruptedException {
        log.info("callable");
        return () -> {
            log.info("async");
            Thread.sleep(2000);
            return "Hello";
        };
    }
//    @GetMapping("/callable")
//    public String callable() throws InterruptedException {
//        log.info("async");
//        Thread.sleep(2000);
//        return "hello";
//    }

    Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();
    @GetMapping("/dr")
    public DeferredResult<String> dr() throws InterruptedException {
        log.info("dr");
        DeferredResult<String> dr = new DeferredResult<>();
        results.add(dr);
        return dr;
    }

    @GetMapping("/dr/count")
    public String drCount(){
        return String.valueOf(results.size());
    }

    @GetMapping("/dr/event")
    public String drEvent(String msg) {
        for (DeferredResult<String> dr: results) {
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }
        return "OK";
    }
}
