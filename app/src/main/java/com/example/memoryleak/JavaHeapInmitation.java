package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class JavaHeapInmitation extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private EditText edt1;
    private EditText edt2;
    private TextView tv1;
    private TextView tv2;
    private boolean isImitating;
    private long maxHeapSize;
    private long tmpHeapSize;

    private List<String> list;

    private MyThread1 t;

    private class MyThread1 extends Thread {
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
                    String s = new String(new char[100000]);
                    list.add(s);
                    tmpHeapSize--;
                    tv1.setText(String.valueOf(tmpHeapSize));
                }
            }, time_per_MB, time_per_MB);
            while (!(Runtime.getRuntime().totalMemory() < maxHeapSize)&& isImitating && maxHeapSize-tmpHeapSize < amount)
            {
            }
            timer.cancel();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_heap_inmitation);

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
        list = new ArrayList<>();
        isImitating = false;
        maxHeapSize = Runtime.getRuntime().maxMemory()/1000000;
        tmpHeapSize = maxHeapSize;
        tv1.setText(String.valueOf(tmpHeapSize));
        tv2.setText("XXX");
        t = new MyThread1();
    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            t.start();
        }
        else{
            isImitating = false;
            list.clear();
            tmpHeapSize = maxHeapSize;
            tv1.setText(String.valueOf(tmpHeapSize));
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }
}