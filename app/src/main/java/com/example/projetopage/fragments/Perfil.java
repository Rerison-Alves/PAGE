package com.example.projetopage.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopage.Config;
import com.example.projetopage.Data.Agrupamento;
import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.example.projetopage.adapters.RecyclerViewAdapterPerfil;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Perfil extends Fragment {
    private DatabaseReference myRef;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Perfil() {

    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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

    Button buttonCriarAgrupamento;

//    String[] gruposDoUsuario= new String[] {"POO em aplicativos", "POO 2022", "Prog. Linear", "Nome de grupo", "Tô sem ideia"};
    Context context;
    RecyclerViewAdapterPerfil recyclerViewAdapterPerfil;
    ArrayList<Grupo> ListadeGrupos = new ArrayList<Grupo>();
    FloatingActionButton config;
    TextView nomeusuariologado, cursousuariologado;
    Aluno aluno=new Aluno();
    FirebaseUser firebaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        firebaseUser = UsuarioAutenticado.UsuarioLogado();
        nomeusuariologado=(TextView) view.findViewById(R.id.nomeusuariologado);
        cursousuariologado=(TextView) view.findViewById(R.id.cursousuariologado);
        config=(FloatingActionButton) view.findViewById(R.id.config);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startConfig = new Intent(getActivity(), Config.class);
                startActivity(startConfig);
            }
        });
        nomeusuariologado.setText(firebaseUser.getDisplayName());
        context= getContext();
        inicializarfirebase();
        ConsultaGrupos(view);
        //BottomSheetDialog
        buttonCriarAgrupamento =  (Button) view.findViewById(R.id.btn_novoagrupamento);
        buttonCriarAgrupamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.bottomsheetcriargrupo(getParentFragmentManager());
            }
        });
        getAluno();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    Intent startMain = new Intent(getActivity(), MainActivity.class);
                    startActivity(startMain);
                    getActivity().finish();
                    FirebaseAuth.getInstance().signOut();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
        return view;
    }

    private void getAluno() {
        DatabaseReference users = myRef.child("Usuario").child(firebaseUser.getUid());
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aluno = snapshot.getValue(Aluno.class);
                nomeusuariologado.setText(UsuarioAutenticado.UsuarioLogado().getDisplayName());
                cursousuariologado.setText(aluno.getCurso());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void listaDeGrupos(View view) {
        RecyclerView usuariogpsView = (RecyclerView) view.findViewById(R.id.gpsusuario);
        RecyclerView.LayoutManager recycleLayoutUsuario = new LinearLayoutManager(context) ;
        usuariogpsView.setLayoutManager(recycleLayoutUsuario);
        recyclerViewAdapterPerfil = new RecyclerViewAdapterPerfil(context, getParentFragmentManager() ,ListadeGrupos);
        usuariogpsView.setAdapter(recyclerViewAdapterPerfil);
        usuariogpsView.setNestedScrollingEnabled( false );
    }

    private void ConsultaGrupos(View view) {
        Query query = myRef.child("Agrupamentos").orderByChild("nome");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListadeGrupos.clear();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Grupo grupo = objsnapshot.getValue(Grupo.class);
                    Query query = myRef.child("UsuarioAgrupamento");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot objsnapshot:snapshot.getChildren()){
                                UsuarioAgrupamento usuarioAgrupamento = objsnapshot.getValue(UsuarioAgrupamento.class);
                                if(usuarioAgrupamento.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())&&usuarioAgrupamento.isAdm()){
                                    if(grupo.getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                                        ListadeGrupos.add(grupo);
                                    }
                                }
                            }
                            listaDeGrupos(view);
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