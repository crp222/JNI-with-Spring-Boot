package com.api.simapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.api.simapi.config.LastUser;
import com.jni.Balls.Balls;

@Controller
public class Example2Controller {


    @Autowired
    LastUser lastUser;

    @MessageMapping("/test")
    @SendTo("/broker/test")
    public String test(){
        System.out.println("Test!");
        return "Hello!";
    }

    @MessageMapping("/getballs")
    @SendTo("/broker/ballpositions")
    public String positions() {
        Balls.Pos[] coords = new Balls().ballPositions(Balls.Pos.class);
        StringBuilder coords_str = new StringBuilder();
        for(Balls.Pos p : coords){
            coords_str.append(p.x+"x"+p.y);
            coords_str.append(",");
        }
        return coords_str.toString();
    }

    @MessageMapping("/mousepos")
    public void mousePos(String message){
        try {
            String parts[] = message.split("-");
            int position_x = Integer.parseInt(parts[0]);
            int position_y = Integer.parseInt(parts[1]);
            int id = Integer.parseInt(parts[2]);
            if(id == lastUser.id){
                new Balls().mousePos(position_x, position_y);
            }
        }catch(Exception e){}
    }
}
