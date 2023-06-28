package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class NativeHeap extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_native_heap);

        ImageButton back = findViewById(R.id.back);
        Button btn1 = findViewById(R.id.NativeHeapImitationMalloc);
        Button btn2 = findViewById(R.id.NativeHeapImitationBitmap);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.NativeHeapImitationMalloc){
            startActivity(new Intent(this, NativeHeapImitationMalloc.class));
        }
        else if(v.getId() == R.id.NativeHeapImitationBitmap){
            startActivity(new Intent(this, NativeHeapImitationBitmap.class));
        }
    }
}