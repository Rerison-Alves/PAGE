package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
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

import com.example.projetopage.Data.Agendamento;
import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    TextView Tema, Data, Horario, Local, descricao;
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
        Data = (TextView) findViewById(R.id.data);
        Horario = (TextView) findViewById(R.id.horario);
        Local = (TextView) findViewById(R.id.local);
        btn_chat = (LinearLayout) findViewById(R.id.btn_chat);
        Tema.setText(encontro.getTema());
        descricao.setText(encontro.getDescricao());
        getcampos();
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = myRef.child("Agrupamentos").child(encontro.getIdAgrupamento())
                        .child("Encontros").child(encontro.getIdEncontro());
                MainActivity.consultaChat(encontro, context, fragmentManager, reference);
            }
        });
    }

    private void getcampos() {
        Query query = myRef.child("Agendamentos");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Agendamento agendamento = objsnapshot.getValue(Agendamento.class);
                    if(agendamento.getIdEncontro().equals(encontro.getIdEncontro())){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatahorario = new SimpleDateFormat("HH:mm");
                        Date datainicio=agendamento.getDataInicio(), datafim=agendamento.getDataFim();
                        Data.setText(formatadata.format(datainicio));
                        String horarios=formatahorario.format(datainicio)+"h - "+formatahorario.format(datafim)+"h";
                        Horario.setText(horarios);
                        if(agendamento.getIdLocal().equals("Online")){
                            Local.setText(agendamento.getIdLocal());
                        }else {
//                           Local.setText();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void inicializarfirebase(){
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }
}