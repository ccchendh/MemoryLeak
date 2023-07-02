package com.example.leak;

import java.util.List;

public class IonLeak {
    public static List<Long> vec;

    public static void toLeak () {
        //调用JNI接口

    }

    public static void toReclaim() {

    }
}
