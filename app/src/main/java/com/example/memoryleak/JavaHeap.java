package com.example.memoryleak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class JavaHeap extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_heap);

        ImageButton back = findViewById(R.id.back);
        Button btn1 = findViewById(R.id.JavaHeapImitationString);
        Button btn2 = findViewById(R.id.JavaHeapImitationOpenfile);
        Button btn3 = findViewById(R.id.JavaHeapImitationInnerclass);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.back){
            finish();
        }
        else if(v.getId() == R.id.JavaHeapImitationString){
            startActivity(new Intent(this, JavaHeapImitationString.class));
        }
        else if(v.getId() == R.id.JavaHeapImitationOpenfile){
            startActivity(new Intent(this, JavaHeapImitationFile.class));
        }
        else if(v.getId() == R.id.JavaHeapImitationInnerclass){
            startActivity(new Intent(this, JavaHeapImitationInnerclass.class));
        }
    }
}