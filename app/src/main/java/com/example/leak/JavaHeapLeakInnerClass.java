package com.example.leak;

import com.example.memoryleak.JavaHeapImitationInnerclass;

import java.util.List;

public class JavaHeapLeakInnerClass {

    public  void toLeak () {
        class Test{
            char[] c = new char[1024 * 512];
        };
        Test test= new Test();

        JavaHeapImitationInnerclass.vec.add(test);
    }

    public  void toReclaim() {
        JavaHeapImitationInnerclass.vec.clear();
    }
}
