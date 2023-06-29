package com.example.leak;

import android.graphics.Bitmap;

import java.util.List;

public class NativeHeapLeakBitmap {

    public static List<Bitmap> vec;

    public static void toLeak () {
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        vec.add(bitmap);
    }

    public static void toReclaim() {
        for (Bitmap bitmap : vec) {
            bitmap.recycle();
        }
        vec.clear();
    }
}
