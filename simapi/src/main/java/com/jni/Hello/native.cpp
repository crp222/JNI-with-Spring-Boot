#include "com_jni_Hello_Hello.h"

using namespace std;

JNIEXPORT jstring JNICALL Java_com_jni_Hello_Hello_sayHello
  (JNIEnv * env, jobject) {
    return env->NewStringUTF("Hello!");
}

