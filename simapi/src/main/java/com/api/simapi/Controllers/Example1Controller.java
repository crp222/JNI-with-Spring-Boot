package com.api.simapi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.simapi.MandelbrotJava.Complex;
import com.api.simapi.MandelbrotJava.MandelbrotJava;
import com.jni.Mandelbrot.Mandelbrot;

@RestController
public class Example1Controller {
    
    @GetMapping("/hello")
    public String hello() {
        return new com.jni.Hello.Hello().sayHello();
    }

    @CrossOrigin
    @GetMapping("/mandelbrot")
    public ResponseEntity<int[]> mandelbrot(@RequestParam String x_s,
                                                    @RequestParam String y_s,
                                                    @RequestParam String zoom_s,
                                                    @RequestParam String maxiter_s) {
        float x = 0,y = 0,zoom = 1;
        int maxiter = 100;
        try {
            x = Float.parseFloat(x_s);
            y = Float.parseFloat(y_s);
            zoom = Float.parseFloat(zoom_s);
            maxiter = Integer.parseInt(maxiter_s);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Mandelbrot mandelbrot = new Mandelbrot();
        return ResponseEntity.ok(mandelbrot.coords(x, y, zoom, maxiter));
    }

    @CrossOrigin
    @GetMapping("/mandelbrotJava")
    public ResponseEntity<int[]> mandlebrotJAVA(@RequestParam String x_s,
                                                        @RequestParam String y_s,
                                                        @RequestParam String zoom_s,
                                                        @RequestParam String maxiter_s) {
        double x = 0,y = 0,zoom = 1;
        int maxiter = 100;
        try {
            x = Double.parseDouble(x_s);
            y = Double.parseDouble(y_s);
            zoom = Double.parseDouble(zoom_s);
            maxiter = Integer.parseInt(maxiter_s);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int[] coords = new int[1000*1000];
        int k = 0;
        for(int i=-500;i<500;i++){
            for(int j=-500;j<500;j++){
                int color = MandelbrotJava.mandelbrot(new Complex(x+i*zoom,y+j*zoom),maxiter);
                coords[k] = color;
                k++;
            }
        }
        return ResponseEntity.ok(coords);
    }

}
