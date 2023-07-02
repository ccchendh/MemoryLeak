package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.leak.JavaHeapLeakString;
import com.example.utils.MemoryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GPUImitation extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;

    private Button btn2;

    private EditText edt1;

    private EditText edt2;

    private TextView tv1;

    private boolean isImitating;

    private FragmentManager fragmentManager;

    private Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpu_imitation);

        ImageButton back = findViewById(R.id.back);
        btn1 = findViewById(R.id.start);
        btn2 = findViewById(R.id.end);
        edt1 = findViewById(R.id.GPU_edittext_MB);
        edt2 = findViewById((R.id.GPU_edittext_s));
        tv1 = findViewById(R.id.GraphicsAmount);

        btn1.setEnabled(true);
        btn2.setEnabled(false);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        isImitating = false;

        fragmentManager = getSupportFragmentManager();

        tv1.setText("xxx");

    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.start){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tv1.setText("xxx");
                }
            }, 1000L, 1000L);

            // 在Activity中启动GPUView Fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GPUView gpuViewFragment = new GPUView();
            fragmentTransaction.add(R.id.GPUFragment, gpuViewFragment);
            fragmentTransaction.commit();

            btn1.setEnabled(false);
            btn2.setEnabled(true);
        }
        else{
            timer.cancel();
            GPUView gpuViewFragment = (GPUView) fragmentManager.findFragmentById(R.id.GPUFragment);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (gpuViewFragment != null) {
                fragmentTransaction.remove(gpuViewFragment);
                fragmentTransaction.commit();
            }
            btn1.setEnabled(true);
            btn2.setEnabled(false);
        }
    }
}