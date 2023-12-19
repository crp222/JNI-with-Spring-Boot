#include "com_jni_Mandelbrot_Mandelbrot.h"
#include <complex>
#include <iostream>

using namespace std;

JNIEXPORT jintArray JNICALL Java_com_jni_Mandelbrot_Mandelbrot_coords
  (JNIEnv * env, jobject, jfloat x, jfloat y, jfloat zoom, jint MAX_ITER) {
    long * coords = new long[1000*1000];
    long k = 0;
    for(int i=-500;i<500;i++){
      for(int j=-500;j<500;j++){
        complex<float> c(x+i*zoom,y+j*zoom);
        complex<float> z(0,0);
        int n = 0;
        while(abs(z) <= 2.0 && n < MAX_ITER){
          z = (z*z)+c;
          n++;
        }
        coords[k] = n;
        k++;
      }
    }
    jintArray jcoords = env->NewIntArray(1000*1000);
    env->SetIntArrayRegion(jcoords,0,1000*1000,coords);
    return jcoords;
}

