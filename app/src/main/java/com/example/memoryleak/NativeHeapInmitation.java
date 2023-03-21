package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NativeHeapInmitation extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private EditText edt1;
    private EditText edt2;
    private TextView tv1;
    private TextView tv2;
    private boolean isImitating;
    private long maxHeapSize;
    private long tmpHeapSize;

    private List<byte[]> memoryBlocks;

    private static MyThread2 t;

    private class MyThread2 extends Thread {
        private Timer timer = new Timer();
        public void run() {
            int amount = Integer.parseInt(edt1.getText().toString());
            int time_per_MB = Integer.parseInt(edt2.getText().toString()) * 1000 / amount ;
            if(amount  > maxHeapSize)
                return;
            btn2.post(new Runnable() {
                @Override
                public void run() {
                    btn2.setEnabled(true);
                }
            });
            btn1.post(new Runnable() {
                @Override
                public void run() {
                    btn1.setEnabled(false);
                }
            });
            isImitating = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    byte[] s = new byte[1000000];
                    memoryBlocks.add(s);
                    tmpHeapSize--;
                    tv1.setText(String.valueOf(tmpHeapSize));
                }
            }, time_per_MB, time_per_MB);
            while (!(Debug.getNativeHeapAllocatedSize() < maxHeapSize)&& isImitating && maxHeapSize-tmpHeapSize < amount)
            {
            }
            timer.cancel();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_heap_inmitation);

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
        memoryBlocks = new ArrayList<>();
        isImitating = false;
        maxHeapSize = Debug.getNativeHeapSize()/1000000;
        tmpHeapSize = maxHeapSize;
        tv1.setText(String.valueOf(tmpHeapSize));
        tv2.setText("XXX");

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            t = new MyThread2();
            t.start();
        }
        else{
            isImitating = false;
            for (byte[] block : memoryBlocks) {
                block = null;
            }
            memoryBlocks.clear();
            tmpHeapSize = maxHeapSize;
            tv1.setText(String.valueOf(tmpHeapSize));
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }
}