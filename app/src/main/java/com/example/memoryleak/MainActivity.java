package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.JavaHeap_imitation);
        Button btn2 = findViewById(R.id.NativeHeap_imitation);
        Button btn3 = findViewById(R.id.GPU_imitation);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.JavaHeap_imitation){
            startActivity(new Intent(this, JavaHeap.class));
        }
        else if(v.getId() == R.id.NativeHeap_imitation){
            startActivity(new Intent(this, NativeHeap.class));
        }
        else{
            startActivity(new Intent(this, GPUImitation.class));
        }
    }
}