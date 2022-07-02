package com.example.projetopage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetopage.Data.Agrupamento;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cima = (Button) findViewById(R.id.btn_cima);

        //teste do banco
        Grupo POO = new Grupo((int)System.currentTimeMillis(), "POO de amigo", "Alguma coisa legal",
                "Ciência da comp", null, "Programação");
        POO.salvar();
        //fim do teste

        cima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirtela2(view);
            }
        });

    }
    //teste2
    public void abrirtela2(View v){
        Intent ittela2 = new Intent(this,AbaPrincipal.class);
        startActivity(ittela2);
    }

    static public void bottomsheetdialog(FragmentManager fragmentManager){
        BottomSheetCriarGrupo bottomSheetCriarGrupo = new BottomSheetCriarGrupo();
        bottomSheetCriarGrupo.show(fragmentManager,"TAG");
    }

    static public void fragmentdialog(FragmentManager fragmentManager){
        PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
        popupDialogFragment.show(fragmentManager, "TAG");
    }
}