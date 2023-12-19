package com.jni.Hello;

public class Hello {
    
    static String folderName = "Hello";

    static {
        System.load(System.getProperty("user.dir")+"\\src\\main\\java\\com\\jni\\"+folderName+"\\native.dll");
     }
   
     public native String sayHello();
   
}


/*
#include "com_jni_Hello_Hello.h"

using namespace std;

JNIEXPORT jstring JNICALL Java_com_jni_Hello_Hello_sayHello
  (JNIEnv * env, jobject) {
    return env->NewStringUTF("Hello!");
}
 */