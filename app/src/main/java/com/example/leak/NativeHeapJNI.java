package com.example.leak;

public class NativeHeapJNI {
    static {
        System.loadLibrary("nativeheapleak");
    }
    public static native long m_malloc(int size);

    public static native void m_free(long ptr);
}
