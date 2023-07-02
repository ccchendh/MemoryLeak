package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.leak.IonLeak;
import com.example.leak.NativeHeapLeakMalloc;
import com.example.utils.MemoryUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IonImitation extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;

    private Button btn2;

    private EditText edt1;

    private EditText edt2;

    private TextView tv1;


    private boolean isImitating;

    private IONLeakMallocThread t;

    private class IONLeakMallocThread extends Thread {
        private final Timer timer = new Timer();
        public void run() {
            tv1.setText("xxx");
            MemoryUtils.MemoryInfoLog();

            int amount = Integer.parseInt(edt1.getText().toString());
            int time = Integer.parseInt(edt2.getText().toString());
            Log.i("Thread info", "NativeHeapLeakMallocThread: Process ID: " + Os.getpid() + ", Parent Process ID:" + Os.getppid() + ", Thread ID: " + Os.gettid());

            btn1.post(() -> btn1.setEnabled(false));
            btn2.post(() -> btn2.setEnabled(true));

            isImitating = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tv1.setText("xxx");
                    MemoryUtils.MemoryInfoLog();
                }
            }, 1000L, 1000L);

            int need;
            //1s泄漏量
            need = amount / time ;
            while(amount > 0 && isImitating) {
                if(amount >= need) {
                    amount -= need;
                } else {
                    //最后一次泄漏量
                    need = amount;
                    amount = 0;
                }
                for(int i = 0; i < need; ++i) {
                    IonLeak.toLeak();
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
        setContentView(R.layout.activity_ion_imitaation);

        ImageButton back = findViewById(R.id.back);
        btn1 = findViewById(R.id.start);
        btn2 = findViewById(R.id.end);
        edt1 = findViewById(R.id.ION_edittext_MB);
        edt2 = findViewById((R.id.ION_edittext_s));
        tv1 = findViewById(R.id.IONAmount);

        btn1.setEnabled(true);
        btn2.setEnabled(false);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        isImitating = false;

        NativeHeapLeakMalloc.vec = new ArrayList<>();
        tv1.setText("xxx");

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            t = new IONLeakMallocThread();
            t.start();
        }
        else{
            isImitating = false;

            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("join() 函数抛出了 InterruptedException 异常：" + e.getMessage());
            }

            IonLeak.toReclaim();
            Runtime.getRuntime().gc();

            tv1.setText("xxx");

            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }

}