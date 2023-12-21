package com.api.simapi.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jni.Balls.Balls;

@RestController
public class Example2RestController {
        
    @CrossOrigin
    @GetMapping("/balls")
    public void start() {
        new Balls().start();
    }
}
