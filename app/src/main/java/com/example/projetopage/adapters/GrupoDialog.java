package com.example.projetopage.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.Usuario;
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

import java.util.ArrayList;

public class GrupoDialog extends Dialog {
    private DatabaseReference myRef;
    Aluno admobj=new Aluno();
    Grupo grupo;
    Context context;
    FragmentManager fragmentManager;
    ArrayList<Encontro> ListadeEncontros= new ArrayList<Encontro>();

    public GrupoDialog(Grupo grupo, Context context, FragmentManager fragmentManager, int themeResId) {
        super(context, themeResId);
        this.grupo=grupo;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    TextView nomeGrupo, areaestudo,descricao, adm;
    Button btn_novoencontro;
    RecyclerView listaEncontros;
    RecyclerViewAdapterEncontros recyclerViewAdapterEncontros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultargrupo);
        inicializarfirebase();
        Consulta();
        nomeGrupo=(TextView) findViewById(R.id.nomeGrupo);
        nomeGrupo.setText(grupo.getNome());
        descricao=(TextView) findViewById(R.id.descricao);
        descricao.setText(grupo.getDescricao());
        areaestudo=(TextView) findViewById(R.id.areaestudo);
        areaestudo.setText(grupo.getAreadeEstudo());
        adm=(TextView) findViewById(R.id.adm);
        getAdm();
        btn_novoencontro=(Button) findViewById(R.id.btn_novoencontro);
        btn_novoencontro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bottomsheetcriarencontro(fragmentManager, grupo.getIdAgrupamento());
            }
        });

    }

    private void getAdm() {
        Query query = myRef.child("UsuarioAgrupamento");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    UsuarioAgrupamento usuarioAgrupamento = objsnapshot.getValue(UsuarioAgrupamento.class);
                    if(usuarioAgrupamento.getIdAgrupmaneto().equals(grupo.getIdAgrupamento())
                    &&usuarioAgrupamento.isAdm()){
                        DatabaseReference users = myRef.child("Usuario").child(usuarioAgrupamento.getIdUsuario());
                        users.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                admobj = snapshot.getValue(Aluno.class);
                                adm.setText(admobj.getNome());
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listaDeEncontros() {
        context=getContext();
        listaEncontros = (RecyclerView) findViewById(R.id.listaEncontros);
        RecyclerView.LayoutManager recycleLayoutEncontros = new LinearLayoutManager(context) ;
        listaEncontros.setLayoutManager(recycleLayoutEncontros);
        recyclerViewAdapterEncontros = new RecyclerViewAdapterEncontros(context, fragmentManager, ListadeEncontros);
        listaEncontros.setAdapter(recyclerViewAdapterEncontros);
        listaEncontros.setNestedScrollingEnabled( false );
    }

    private void Consulta() {
        Query queryListadeEncontros= myRef.child("Agrupamentos").child(String.valueOf(grupo.getIdAgrupamento())).child("Encontros").orderByChild("tema");
        queryListadeEncontros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListadeEncontros.clear();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Encontro encontro = objsnapshot.getValue(Encontro.class);
                    ListadeEncontros.add(encontro);
                }
                listaDeEncontros();
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