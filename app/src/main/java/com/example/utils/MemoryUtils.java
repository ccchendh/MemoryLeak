package com.example.utils;

import android.os.Debug;
import android.util.Log;

import java.util.Locale;

public class MemoryUtils {

    public static long getJavaHeap() {
        return (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024);
    }

    public static long getNativeHeap() {
        return Debug.getNativeHeapAllocatedSize()/(1024*1024);
    }
    public static long getPssMemory() {
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        return memoryInfo.getTotalPss()/1024;
    }
    public static void MemoryInfoLog() {
        String JavaHeapSize = String.format(Locale.US, "JavaHeap-size : %d MB", getJavaHeap());
        Log.i("JavaHeap", JavaHeapSize);
        String NativeHeapSize = String.format(Locale.US, "NativeHeap-size : %d MB", getNativeHeap());
        Log.i("NativeHeap", NativeHeapSize);
        String PssSize = String.format(Locale.US, "Pss-size : %d MB", getPssMemory());
        Log.i("Pss", PssSize);
    }
}
