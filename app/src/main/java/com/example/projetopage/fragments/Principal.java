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
import android.widget.Toast;

import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.R;
import com.example.projetopage.adapters.RecyclerViewAdapterEncontrosPrincipal;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Principal extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    DatabaseReference myRef;
    String[] testeRecentes= new String[] {"POO em aplicativos", "POO 2022", "Prog. Linear", "Nome de grupo", "Tô sem ideia"};
    ArrayList<Grupo> listaTodos = new ArrayList<>();
    ArrayList<Encontro> ListadeEncontros= new ArrayList<>();
    Context context;
    RecyclerViewAdapterRecentes recyclerViewAdapterRecentes;
    RecyclerViewAdapterEncontrosPrincipal recyclerViewAdapterEncontrosPrincipal;
    RecyclerViewAdapterExtended recyclerViewAdapterExtended;
    private RecyclerView todosView, encontrosView, recentesView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        inicializarfirebase();
        context= getContext();
        //Todos Grupos
        todosView = (RecyclerView) view.findViewById(R.id.gpstodosgrupos);
        todosView.setNestedScrollingEnabled( false );
        ConsultaTodos();
        //Grupos Recentes
        recentesView = (RecyclerView) view.findViewById(R.id.gpsrecentes);

        //Próximos Encontros
        encontrosView = (RecyclerView) view.findViewById(R.id.gpsencontros);
        ConsultaEncontros();
        return view;
    }

    private void listarRecentes() {
        RecyclerView.LayoutManager recycleLayoutRecentes = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recentesView.setLayoutManager(recycleLayoutRecentes);
        recyclerViewAdapterRecentes = new RecyclerViewAdapterRecentes(context, requireActivity().getSupportFragmentManager(), listaTodos);
        recentesView.setAdapter(recyclerViewAdapterRecentes);
    }

    private void listarTodos(RecyclerView recyclerView) {
        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recycleLayoutTodos);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, requireActivity().getSupportFragmentManager(), listaTodos);
        recyclerView.setAdapter(recyclerViewAdapterExtended);
    }

    private void listarEncontros(RecyclerView recyclerView) {
        RecyclerView.LayoutManager recycleLayoutEncontros = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recycleLayoutEncontros);
        Set<Encontro> set = new HashSet<>(ListadeEncontros);
        ListadeEncontros.clear();
        ListadeEncontros.addAll(set);
        recyclerViewAdapterEncontrosPrincipal = new RecyclerViewAdapterEncontrosPrincipal(context, requireActivity().getSupportFragmentManager(), ListadeEncontros);
        recyclerView.setAdapter(recyclerViewAdapterEncontrosPrincipal);
    }

    private void ConsultaEncontros(){
        Query query = myRef.child("Agrupamentos").orderByChild("nome");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Grupo> todos = new ArrayList<>();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Grupo grupo = objsnapshot.getValue(Grupo.class);
                    Query query = myRef.child("UsuarioAgrupamento");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot objsnapshot:snapshot.getChildren()){
                                UsuarioAgrupamento usuarioAgrupamento = objsnapshot.getValue(UsuarioAgrupamento.class);
                                assert usuarioAgrupamento != null;
                                if(usuarioAgrupamento.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())){
                                    assert grupo != null;
                                    if(grupo.getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                                        todos.add(grupo);
                                    }
                                }
                            }
                            for (Grupo grupoi:todos){
                                Query queryListadeEncontros= myRef.child("Agrupamentos").child(String.valueOf(grupoi.getIdAgrupamento())).child("Encontros").orderByChild("tema");
                                queryListadeEncontros.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot objsnapshot:snapshot.getChildren()){
                                            Encontro encontro = objsnapshot.getValue(Encontro.class);
                                            ListadeEncontros.add(encontro);
                                        }
                                        Set<Encontro> set = new LinkedHashSet<>(ListadeEncontros);
                                        ListadeEncontros.clear();
                                        ListadeEncontros.addAll(set);
                                        listarEncontros(encontrosView);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                                assert usuarioAgrupamento != null;
                                if(usuarioAgrupamento.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())
                                        &&!usuarioAgrupamento.isAdm()){
                                    assert grupo != null;
                                    if(grupo.getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                                        listaTodos.add(grupo);
                                    }
                                }
                            }
                            listarTodos(todosView);
                            listarRecentes();
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