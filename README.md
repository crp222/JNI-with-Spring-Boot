

This software is using the following libraries:
+ box2d (https://github.com/erincatto/box2d)

# JNI with Spring-Boot

+ use jni with spring-boot
+ small speed comparison example

# How to

You can just start the spring boot app, located in simapi directory, and the vite app in simview/example1 directory and you can see speed comparasion on localhost:3030 (or on what domain your vite uses)

To write more jni functions write your java jni classes, the class needs to be in a folder inside the jni directory, and the name of the class needs to be the same as the name of the folder wich contains it.
Then you can run the make.py script, it makes a native.cpp file where you can implement your function. (All the other native.cpp files will be remade)
Then run the compile.py script.
