package com.example.leak;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class JavaHeapLeakFile extends Leak{

    public static List<BufferedOutputStream> vec;

    public static void toLeak() throws RuntimeException{
        BufferedOutputStream Bufos = new BufferedOutputStream(System.out, 1024*1024);
        byte[] data = new byte[1024 * 1000];
        try{
            Bufos.write(data);
        }catch (IOException e) {
            System.err.println("write() 函数抛出了 IOException 异常：" + e.getMessage());
        }
        vec.add(Bufos);
    }

    public static void toReclaim() {
        for(BufferedOutputStream os : vec) {
            try {
                os.close();
            } catch (IOException e) {
                System.err.println("close() 函数抛出了 IOException 异常：" + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("close() 函数抛出了 NullPointerException 异常：" + e.getMessage());
            }
        }
        vec.clear();
    }
}
