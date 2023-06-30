package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.leak.JavaHeapLeakFile;
import com.example.utils.MemoryUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class JavaHeapImitationFile extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private EditText edt1;
    private EditText edt2;
    private TextView tv1;
    private TextView tv2;
    private boolean isImitating;

    private JavaHeapLeakFileThread t;

//    private long maxHeapSize;
//    private long tmpHeapSize;
//    private List<String> list;

    private class JavaHeapLeakFileThread extends Thread {
        private final Timer timer = new Timer();
        public void run() {
            tv1.setText(String.valueOf(MemoryUtils.getJavaHeap()));
            tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));
            MemoryUtils.MemoryInfoLog();
            int amount = Integer.parseInt(edt1.getText().toString());
            int time = Integer.parseInt(edt2.getText().toString()) ;
            if(amount  > MemoryUtils.getMaxJavaHeap()) {
                amount = (int)(MemoryUtils.getMaxJavaHeap())-5;
                String MaxJavaHeapSize = String.format(Locale.US, "MaxJavaHeap-size : %d MB", amount);
                Log.i("MaxJavaHeap", MaxJavaHeapSize);
            }
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
                    tv1.setText(String.valueOf(MemoryUtils.getJavaHeap()));
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
                    try {
                        JavaHeapLeakFile.toLeak();
                    } catch (RuntimeException e) {
                        System.err.println("toLeak() 函数抛出了 RuntimeException 异常：" + e.getMessage());
                    }

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
        setContentView(R.layout.activity_java_heap_imitation);

        ImageButton back = findViewById(R.id.back);
        btn1 = findViewById(R.id.start);
        btn2 = findViewById(R.id.end);
        edt1 = findViewById(R.id.Java_edittext_MB);
        edt2 = findViewById((R.id.Java_edittext_s));
        tv1 = findViewById(R.id.JavaHeapAmount);
        tv2 = findViewById(R.id.JavaPSSAmount);

        btn1.setEnabled(true);
        btn2.setEnabled(false);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
//        list = new ArrayList<>();
        JavaHeapLeakFile.vec = new ArrayList<>();
        isImitating = false;
//        maxHeapSize = (Runtime.getRuntime().totalMemory()/(1024*1024));
//        tmpHeapSize = maxHeapSize;
        tv1.setText(String.valueOf(MemoryUtils.getJavaHeap()));
        tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));

    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            t = new JavaHeapLeakFileThread();
            t.start();
//            btn1.setEnabled(false);
//            btn2.setEnabled(true);
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    tv1.setText(String.valueOf((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/(1024*1024)));
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
//            tmpHeapSize = maxHeapSize;
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("join() 函数抛出了 InterruptedException 异常：" + e.getMessage());
            }
            JavaHeapLeakFile.toReclaim();
            Runtime.getRuntime().gc();
            tv1.setText(String.valueOf(MemoryUtils.getJavaHeap()));
            tv2.setText(String.valueOf(MemoryUtils.getPssMemory()));
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }
}