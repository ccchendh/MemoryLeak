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

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.JavaHeap_imitation){
            startActivity(new Intent(this, JavaHeapInmitation.class));
        }
        else{
            startActivity(new Intent(this, NativeHeapInmitation.class));
        }
    }
}