package com.example.leak;

import java.util.List;

public class JavaHeapLeak {
    public static List<String> vec;
    public static void excute () {

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < 1024 * 512 - 20; i++) {
            sb.append("\0");
        }
        String s = sb.toString();

        vec.add(s);
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
    public static void toReclaim() {
        vec.clear();
    }
}
