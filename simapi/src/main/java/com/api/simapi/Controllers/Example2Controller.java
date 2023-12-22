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

    @MessageMapping("/mousepos")
    public void mousePos(String message){
        try {
            String position_xy_str[] = message.split("-");
            int position_x = Integer.parseInt(position_xy_str[0]);
            int position_y = Integer.parseInt(position_xy_str[1]);
            new Balls().mousePos(position_x, position_y);
        }catch(Exception e){}
    }
}
