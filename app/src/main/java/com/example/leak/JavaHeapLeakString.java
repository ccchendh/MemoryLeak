package com.example.leak;

import com.example.memoryleak.JavaHeapImitationString;

import java.util.List;

public class JavaHeapLeakString {

    public void toLeak () {

        char[] s = new char[1024*512];
        char valueToFill = '\0'; // 填充数组的字符

        for (int i = 0; i < s.length; i++) {
            s[i] = valueToFill;
        }

        JavaHeapImitationString.vec.add(s);
    }
//    public void toLeak(int count, int time) throws InterruptedException {
//        vec = new ArrayList<>();
//        int need = 0;
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
//                excute();
//            }
//            Thread.sleep(5000L);
//        }
//    }
    public void toReclaim() {
        JavaHeapImitationString.vec.clear();
        JavaHeapImitationString.vec = null;
    }
}
