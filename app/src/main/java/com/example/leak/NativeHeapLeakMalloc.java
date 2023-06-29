package com.example.leak;


import java.util.List;

public class NativeHeapLeakMalloc {
    public static List<Long> vec;

    public static void toLeak () {
        long ptr = NativeHeapJNI.m_malloc(1024*1024);
        vec.add(ptr);
    }
//    public void toLeak(int count, int time) throws InterruptedException {
//        int need = 0;
//        long ptr = 0;
//        //5s泄漏量
//        need = count / time * 5;
//        while(count > 0) {
//            if(count >= need) {
//                count -= need;
//            } else {
//                //最后一次泄漏量
//                need = count;
//                count = 0;
//            }
//            for(int i = 0; i < need; ++i) {
//                ptr = NativeHeapJNI.m_malloc(1024*1024);
//                vec.add(ptr);
//            }
//            Thread.sleep(5000L);
//        }
//        Log.d("Tag","222222222222");
//    }
    public static void toReclaim() {
        for (long ptr : vec) {
            NativeHeapJNI.m_free(ptr);
        }
        vec.clear();
    }
}
