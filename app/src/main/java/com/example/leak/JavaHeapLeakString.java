package com.example.leak;

import com.example.memoryleak.JavaHeapImitationString;

import java.util.List;

public class JavaHeapLeakString extends Leak {

    public void toLeak () {

        char[] s = new char[1024*512];
        char valueToFill = '\0'; // 填充数组的字符

        for (int i = 0; i < s.length; i++) {
            s[i] = valueToFill;
        }

        JavaHeapImitationString.vec.add(s);
    }

    public void toReclaim() {
        JavaHeapImitationString.vec.clear();
        JavaHeapImitationString.vec = null;
    }
}
