package com.example.utils;

import android.os.Debug;
import android.util.Log;

import com.example.memoryleak.JavaHeap;

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
    public static long getMaxJavaHeap() {
        return (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().freeMemory())/(1024*1024);
    }
    public static void MemoryInfoLog() {

        final Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);

        String javaHeap = memoryInfo.getMemoryStat("summary.java-heap");
        String nativeHeap = memoryInfo.getMemoryStat("summary.native-heap");
        String code = memoryInfo.getMemoryStat("summary.code");
        String stack = memoryInfo.getMemoryStat("summary.stack");
        String graphics = memoryInfo.getMemoryStat("summary.graphics");
        String privateOther = memoryInfo.getMemoryStat("summary.private-other");
        String system = memoryInfo.getMemoryStat("summary.system");
        String swap = memoryInfo.getMemoryStat("summary.total-swap");

        String JavaHeapSize = String.format(Locale.US, "JavaHeap-size : %s MB", Integer.parseInt(javaHeap)/1024);
        Log.i("JavaHeap", JavaHeapSize);
        String NativeHeapSize = String.format(Locale.US, "NativeHeap-size : %s MB", Integer.parseInt(nativeHeap)/1024);
        Log.i("NativeHeap", NativeHeapSize);
        String GraphicsSize = String.format(Locale.US, "Graphics-size : %s MB", Integer.parseInt(graphics)/1024);
        Log.i("GraphicsSize", GraphicsSize);
        String StackSize = String.format(Locale.US, "Stack-size : %s MB", Integer.parseInt(stack)/1024);
        Log.i("StackSize", StackSize);
        String CodeSize = String.format(Locale.US, "Code-size : %s MB", Integer.parseInt(code)/1024);
        Log.i("CodeSize", CodeSize);
        String OtherSize = String.format(Locale.US, "other-size : %s MB", Integer.parseInt(privateOther)/1024);
        Log.i("OtherSize", OtherSize);
//        String PssSize = String.format(Locale.US, "Pss-size : %d MB", getPssMemory());
//        Log.i("Pss", PssSize);
    }
}
