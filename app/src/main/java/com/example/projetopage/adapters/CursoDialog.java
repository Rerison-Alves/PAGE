package com.example.projetopage.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CursoDialog extends Dialog {
    private DatabaseReference myRef;
    String Curso;
    Context context;
    FragmentManager fragmentManager;

    public CursoDialog(String Curso, Context context, FragmentManager fragmentManager, int themeResId) {
        super(context, themeResId);
        this.Curso=Curso;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    private RecyclerViewAdapterExtended recyclerViewAdapterExtended;
    TextView NomeCurso;
    RecyclerView recyclerView;
    ImageView Voltar;
    ArrayList<Grupo> grupos = new ArrayList<Grupo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultacurso);
        inicializarfirebase();
        NomeCurso=(TextView) findViewById(R.id.Curso);
        recyclerView=(RecyclerView) findViewById(R.id.recycle);
        Voltar=(ImageView) findViewById(R.id.voltar);

        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Query query = myRef.child("Agrupamentos").orderByChild("nome");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                grupos.clear();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Grupo grupo = objsnapshot.getValue(Grupo.class);
                    if(grupo.getCurso().equals(Curso)){
                        grupos.add(grupo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recycleLayoutTodos);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, fragmentManager, grupos);
        recyclerView.setAdapter(recyclerViewAdapterExtended);
    }

    public void inicializarfirebase(){
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }
}
