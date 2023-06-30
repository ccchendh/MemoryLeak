#include <stdlib.h>
#include <jni.h>
#include "com_example_leak_NativeHeapJNI.h"

JNIEXPORT jlong JNICALL Java_com_example_leak_NativeHeapJNI_m_1malloc
  (JNIEnv *, jclass, jint size) {
        return (jlong) malloc(size);
  }


JNIEXPORT void JNICALL Java_com_example_leak_NativeHeapJNI_m_1free
  (JNIEnv *, jclass, jlong ptr) {
        free((void*) ptr);
  }