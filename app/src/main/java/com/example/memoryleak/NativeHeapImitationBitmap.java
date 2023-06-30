package com.example.memoryleak;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leak.JavaHeapLeakString;
import com.example.leak.NativeHeapLeakBitmap;
import com.example.leak.NativeHeapLeakMalloc;
import com.example.utils.MemoryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NativeHeapImitationBitmap extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private EditText edt1;
    private EditText edt2;
    private TextView tv1;
    private TextView tv2;
    private boolean isImitating;
    public static List<Bitmap> vec;
    private NativeHeapLeakBitmap leak;
    private NativeHeapLeakBitmapThread t;
//    private long maxHeapSize;
//    private long tmpHeapSize;
//
//    private List<byte[]> memoryBlocks;
//

    private class NativeHeapLeakBitmapThread extends Thread {
        private final Timer timer = new Timer();
        public void run() {
            tv1.setText(String.valueOf(MemoryUtils.getNativeHeap()));
            tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));
            MemoryUtils.MemoryInfoLog();
            int amount = Integer.parseInt(edt1.getText().toString());
            int time = Integer.parseInt(edt2.getText().toString());
//            btn2.post(new Runnable() {
//                @Override
//                public void run() {
//                    btn2.setEnabled(true);
//                }
//            });
//            btn1.post(new Runnable() {
//                @Override
//                public void run() {
//                    btn1.setEnabled(false);
//                }
//            });
            btn1.post(() -> btn1.setEnabled(false));
            btn2.post(() -> btn2.setEnabled(true));
            isImitating = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tv1.setText(String.valueOf(MemoryUtils.getNativeHeap()));
                    tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));
                    MemoryUtils.MemoryInfoLog();
                }
            }, 1000L, 1000L);
            int need;
            //5s泄漏量
            need = amount / time;
            while(amount > 0 && isImitating) {
                if(amount >= need) {
                    amount -= need;
                } else {
                    //最后一次泄漏量
                    need = amount;
                    amount = 0;
                }
                for(int i = 0; i < need; ++i) {
                    leak.toLeak();
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.err.println("sleep() 函数抛出了 InterruptedException 异常：" + e.getMessage());
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                System.err.println("sleep() 函数抛出了 InterruptedException 异常：" + e.getMessage());
            }
            timer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_heap_imitation);

        ImageButton back = findViewById(R.id.back);
        btn1 = findViewById(R.id.start);
        btn2 = findViewById(R.id.end);
        edt1 = findViewById(R.id.Native_edittext_MB);
        edt2 = findViewById((R.id.Native_edittext_s));
        tv1 = findViewById(R.id.NativeHeapAmount);
        tv2 = findViewById(R.id.NativePSSAmount);

        btn1.setEnabled(true);
        btn2.setEnabled(false);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
//        memoryBlocks = new ArrayList<>();
        isImitating = false;
//        maxHeapSize = Debug.getNativeHeapSize()/1000000;
//        tmpHeapSize = maxHeapSize;
//        vec = new ArrayList<>();
        tv1.setText(String.valueOf(MemoryUtils.getNativeHeap()));
        tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            leak = new NativeHeapLeakBitmap();
            vec = new ArrayList<>();
            t = new NativeHeapLeakBitmapThread();
            t.start();
//            btn1.setEnabled(false);
//            btn2.setEnabled(true);
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    tv1.setText(String.valueOf(Debug.getNativeHeapAllocatedSize()/(1024*1024)));
//                }
//            }, 5000L, 5000L);
//            int amount = Integer.parseInt(edt1.getText().toString());
//            int time = Integer.parseInt(edt2.getText().toString());
//            try {
//                leak.toLeak(amount, time);
//            } catch (InterruptedException e) {
//                System.err.println("toLeak() 函数抛出了 InterruptedException 异常：" + e.getMessage());
//            }
        }
        else{
            isImitating = false;
//            for (byte[] block : memoryBlocks) {
//                block = null;
//            }
//            memoryBlocks.clear();
//            tmpHeapSize = maxHeapSize;
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("join() 函数抛出了 InterruptedException 异常：" + e.getMessage());
            }
            leak.toReclaim();
            leak = null;
            Runtime.getRuntime().gc();
            tv1.setText(String.valueOf(MemoryUtils.getNativeHeap()));
            tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }
}
