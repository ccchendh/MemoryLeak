package com.example.leak;


import java.util.List;

public class NativeHeapLeakMalloc extends Leak {
    public static List<Long> vec;

    public static void toLeak () {
        //调用JNI接口
        long ptr = NativeHeapJNI.m_malloc(1024*1024);
        vec.add(ptr);
    }

    public static void toReclaim() {
        for (long ptr : vec) {
            NativeHeapJNI.m_free(ptr);
        }
        vec.clear();
    }
}
