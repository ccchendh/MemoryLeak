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

public class JavaHeapLeakFile {

    public static List<BufferedInputStream> vec;

    public static void toLeak() throws RuntimeException{
//        try {
//            InputStream os = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/example/leak/a.txt");
            // File file = new File("a.txt");
            // InputStream os = new FileInputStream(file);
            BufferedInputStream Bufos = new BufferedInputStream(null, 1024*1024);
//            os.close();
            vec.add(Bufos);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


    public static void toReclaim() {
        for(BufferedInputStream os : vec) {
            try {
                os.close();
            } catch (IOException e) {
                System.err.println("close() 函数抛出了 IOException 异常：" + e.getMessage());
            }
        }
        vec.clear();
    }
}
