package com.jni.Mandelbrot;

public class Mandelbrot {

    static String folderName = "Mandelbrot";

    static {
        System.load(System.getProperty("user.dir")+"\\src\\main\\java\\com\\jni\\"+folderName+"\\native.dll");
     }
   
    public native int[] coords(float x,float y,float zoom,int MAX_ITER);
}

