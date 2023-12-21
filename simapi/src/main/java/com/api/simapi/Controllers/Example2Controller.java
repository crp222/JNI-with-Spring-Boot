package com.api.simapi.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.jni.Balls.Balls;

@Controller
public class Example2Controller {

    @MessageMapping("/test")
    @SendTo("/broker/test")
    public String test(){
        System.out.println("Test!");
        return "Hello!";
    }

    @MessageMapping("/getballs")
    @SendTo("/broker/ballpositions")
    public Balls.Pos[] positions() {
        Balls.Pos[] coords = new Balls().ballPositions(Balls.Pos.class);
        return coords;
    }
}
