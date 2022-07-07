package com.example.projetopage.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EncontroDialog extends Dialog {
    private DatabaseReference myRef;
    Encontro encontro;
    Context context;
    FragmentManager fragmentManager;

    public EncontroDialog(Encontro encontro, Context context, FragmentManager fragmentManager, int themeResId) {
        super(context, themeResId);
        this.encontro=encontro;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    TextView Tema, Data, Horario,descricao;
    ImageView voltar;
    LinearLayout btn_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultarencontro);
        inicializarfirebase();
        Tema = (TextView) findViewById(R.id.Tema);
        descricao = (TextView) findViewById(R.id.descricao);
        voltar = (ImageView) findViewById(R.id.voltar);
        Tema.setText(encontro.getTema());
        descricao.setText(encontro.getDescricao());
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void inicializarfirebase(){
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Agrupamentos").child(String.valueOf(encontro.getIdAgrupamento())).child("Encontros");
    }
}