package com.example.projetopage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cima = (Button) findViewById(R.id.btn_cima);

        cima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirtela2(view);
            }
        });

    }
    //teste2
    public void abrirtela2(View v){
        Intent ittela2 = new Intent(this,Tela2.class);
        startActivity(ittela2);
    }
}