package com.example.projetopage.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.R;
import com.example.projetopage.adapters.RecyclerViewAdapterRecentes;
import com.example.projetopage.adapters.RecyclerViewAdapterExtended;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Principal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Principal() {
        // Required empty public constructor
    }

    public static Principal newInstance(String param1, String param2) {
        Principal fragment = new Principal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    DatabaseReference myRef;
    String[] testeRecentes= new String[] {"POO em aplicativos", "POO 2022", "Prog. Linear", "Nome de grupo", "Tô sem ideia"};
    String[] testeEncontros= new String[] {"Classe Swing", "Ui design"};
    ArrayList<Grupo> listaTodos = new ArrayList<Grupo>();
    Context context;
    RecyclerViewAdapterRecentes recyclerViewAdapterRecentes;
    RecyclerViewAdapterExtended recyclerViewAdapterExtended;
    private RecyclerView todosView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        inicializarfirebase();
        context= getContext();
        //Grupos Recentes
        RecyclerView recentesView = (RecyclerView) view.findViewById(R.id.gpsrecentes);
        RecyclerView.LayoutManager recycleLayoutRecentes = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recentesView.setLayoutManager(recycleLayoutRecentes);
        recyclerViewAdapterRecentes = new RecyclerViewAdapterRecentes(context, testeRecentes);
        recentesView.setAdapter(recyclerViewAdapterRecentes);
        //Próximos Encontros
        RecyclerView encontrosView = (RecyclerView) view.findViewById(R.id.gpsencontros);
        RecyclerView.LayoutManager recycleLayoutEncontros = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        encontrosView.setLayoutManager(recycleLayoutEncontros);
        recyclerViewAdapterRecentes = new RecyclerViewAdapterRecentes(context, testeEncontros);
        encontrosView.setAdapter(recyclerViewAdapterRecentes);
        //Todos Grupos
        todosView = (RecyclerView) view.findViewById(R.id.gpstodosgrupos);
        todosView.setNestedScrollingEnabled( false );
        ConsultaTodos();
        return view;
    }

    private void listarTodos(RecyclerView todosView) {
        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context);
        todosView.setLayoutManager(recycleLayoutTodos);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, getActivity().getSupportFragmentManager(), listaTodos);
        todosView.setAdapter(recyclerViewAdapterExtended);
    }

    private void ConsultaTodos() {
        Query query = myRef.child("Agrupamentos").orderByChild("nome");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaTodos.clear();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Grupo grupo = objsnapshot.getValue(Grupo.class);
                    Query query = myRef.child("UsuarioAgrupamento");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot objsnapshot:snapshot.getChildren()){
                                UsuarioAgrupamento usuarioAgrupamento = objsnapshot.getValue(UsuarioAgrupamento.class);
                                if(usuarioAgrupamento.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())
                                        &&!usuarioAgrupamento.isAdm()){
                                    if(grupo.getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                                        listaTodos.add(grupo);
                                    }
                                }
                            }
                            listarTodos(todosView);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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