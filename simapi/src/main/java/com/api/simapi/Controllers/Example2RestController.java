package com.api.simapi.Controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.simapi.config.LastUser;
import com.jni.Balls.Balls;

@RestController
public class Example2RestController {

    
    @Autowired
    LastUser lastUser;
        
    @CrossOrigin
    @GetMapping("/balls")
    public int start() {
        int id = new Random().nextInt(10000);
        lastUser.id = id;
        new Balls().start();
        return id;
    }
}
