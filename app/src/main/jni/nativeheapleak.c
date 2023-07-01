#include <stdlib.h>
#include <jni.h>
#include "com_example_leak_NativeHeapJNI.h"

JNIEXPORT jlong JNICALL Java_com_example_leak_NativeHeapJNI_m_1malloc
  (JNIEnv *, jclass, jint size) {
         int csize = (int)size;
         char* ptr = (char*) malloc(csize);
         for(int i = 0; i<csize; i++){
            ptr[i] = 'x';
         }
         return (jlong)ptr;
  }


JNIEXPORT void JNICALL Java_com_example_leak_NativeHeapJNI_m_1free
  (JNIEnv *, jclass, jlong ptr) {
        free((char*) ptr);
  }