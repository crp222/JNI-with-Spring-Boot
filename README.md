

This software is using the following libraries:
+ box2d (https://github.com/erincatto/box2d)
+ matter-js (https://github.com/liabru/matter-js)

# JNI with Spring-Boot

+ use jni with spring-boot
+ small speed comparison example

# How to

Open `simview` and run `npm install`

Copy box2d headers to `simapi/src/main/java/com/jni/Balls/box2d`

Build box2d and copy `libbox2d.a` to `simapi/src/main/java/com/jni/Balls/`

Run `python run.py`
