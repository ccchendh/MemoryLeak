package com.example.leak;

import android.graphics.Bitmap;

import com.example.memoryleak.NativeHeapImitationBitmap;

import java.util.List;

public class NativeHeapLeakBitmap {

    public void toLeak () {
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        NativeHeapImitationBitmap.vec.add(bitmap);
    }

    public void toReclaim() {
        for (Bitmap bitmap : NativeHeapImitationBitmap.vec) {
            bitmap.recycle();
        }
        NativeHeapImitationBitmap.vec.clear();
        NativeHeapImitationBitmap.vec = null;
    }
}
