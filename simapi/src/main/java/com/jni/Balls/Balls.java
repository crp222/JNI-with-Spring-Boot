package com.jni.Balls;

public class Balls {
    static public class Pos {
        public float x;
        public float y;
    }

    static String folderName = "Balls";

    static {
        System.load(System.getProperty("user.dir")+"\\src\\main\\java\\com\\jni\\"+folderName+"\\native.dll");
     }
   
    public native void start();

    public native Pos[] ballPositions(Class<?> posClass);

    public native void mousePos(int x,int y);
    
}
