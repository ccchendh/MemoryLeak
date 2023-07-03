package com.example.leak;

import com.example.memoryleak.JavaHeapImitationInnerclass;

import java.util.List;

public class JavaHeapLeakInnerClass extends Leak {

    class Test{
        char[] c = new char[1024 * 512];
    };

    public  void toLeak () {
        Test test= new Test();
        char valueToFill = '\0'; // 填充数组的字符

        for (int i = 0; i < test.c.length; i++) {
            (test.c)[i] = valueToFill;
        }
        JavaHeapImitationInnerclass.vec.add(test);
    }

    public void toReclaim() {
        JavaHeapImitationInnerclass.vec.clear();
        JavaHeapImitationInnerclass.vec = null;
    }
}
