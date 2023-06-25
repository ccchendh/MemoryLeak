package com.example.utils;

import android.os.Debug;

public class MemoryUtils {
    public static long getPssMemory() {
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        return memoryInfo.getTotalPss();
    }
}
