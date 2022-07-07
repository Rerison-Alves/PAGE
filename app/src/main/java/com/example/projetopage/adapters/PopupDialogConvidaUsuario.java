package com.example.projetopage.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Usuario;
import com.example.projetopage.R;
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

public class PopupDialogConvidaUsuario extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private DatabaseReference myRef;
    RecyclerViewAdapterUsuariosTodos recyclerViewAdapterUsuariosTodos;
    RecyclerViewAdapterUsuariosConvidados recyclerViewAdapterUsuariosConvidados;
    RecyclerView ListaConvidados, ListaTodos;
    TextView nconvidados;
    Context context;
    public static HashSet<Usuario> todos = new HashSet<Usuario>(), convidados = new HashSet<Usuario>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context= getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.convidaalunos,null);
        builder.setView(view).setTitle("").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ListaConvidados = (RecyclerView) view.findViewById(R.id.ListaConvidados);
        ListaTodos = (RecyclerView) view.findViewById(R.id.ListaTodos);
        nconvidados = (TextView) view.findViewById(R.id.nconvidados);
        inicializarfirebase();

        ConsultaUsuarios();

        recyclerViewAdapterUsuariosTodos = new RecyclerViewAdapterUsuariosTodos(context, todos);
        setRecyclerUsuarios(recyclerViewAdapterUsuariosTodos, ListaTodos, context);

        myRef.child("Event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recyclerViewAdapterUsuariosTodos = new RecyclerViewAdapterUsuariosTodos(context, todos);
                setRecyclerUsuarios(recyclerViewAdapterUsuariosTodos, ListaTodos, context);

                recyclerViewAdapterUsuariosConvidados = new RecyclerViewAdapterUsuariosConvidados(context, convidados);
                setRecyclerUsuarios(recyclerViewAdapterUsuariosConvidados, ListaConvidados, context);
                nconvidados.setText(convidados.size() + "/50");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return builder.create();
    }

    private void ConsultaUsuarios() {
        Query queryListadeGrupos= myRef.child("Usuario").orderByChild("nome");
        queryListadeGrupos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todos.clear();
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Aluno user = objsnapshot.getValue(Aluno.class);
                    if (!user.getIdUsuario().equals(UsuarioAutenticado.UsuarioLogado().getUid())){
                        todos.add(user);
                    }
                    todos.removeAll(convidados);
                }
                recyclerViewAdapterUsuariosTodos = new RecyclerViewAdapterUsuariosTodos(context, todos);
                setRecyclerUsuarios(recyclerViewAdapterUsuariosTodos, ListaTodos, context);
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

    static public void setRecyclerUsuarios(RecyclerView.Adapter recyclerViewAdapter, RecyclerView recyclerView, Context context){
        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recycleLayoutTodos);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    static public void LimparListas() {
        todos.clear();
        convidados.clear();
    }
}