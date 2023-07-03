package com.example.leak;

import android.graphics.Bitmap;

import com.example.memoryleak.NativeHeapImitationBitmap;

import java.nio.ByteBuffer;
import java.util.List;

public class NativeHeapLeakBitmap extends Leak{

    public void toLeak () {
        int targetSize = 1024 * 1024; // 1MB
        byte[] byteArray = new byte[targetSize];

        // 创建一个1MB大小的Bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        //从将字数组内容复制到Bitmap中
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(byteArray));
        NativeHeapImitationBitmap.vec.add(bitmap);
    }

    public void toReclaim() {
        //销毁
        for (Bitmap bitmap : NativeHeapImitationBitmap.vec) {
            bitmap.recycle();
        }
        NativeHeapImitationBitmap.vec.clear();
        NativeHeapImitationBitmap.vec = null;
    }
}
