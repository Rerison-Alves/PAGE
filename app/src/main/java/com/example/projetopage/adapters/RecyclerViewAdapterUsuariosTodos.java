package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Usuario;
import com.example.projetopage.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

public class RecyclerViewAdapterUsuariosTodos extends RecyclerView.Adapter<RecyclerViewAdapterUsuariosTodos.ViewHolder> {
    HashSet<Usuario> usuarios;
    Context context;
    View view;
    ViewHolder viewHolder;

    public RecyclerViewAdapterUsuariosTodos(Context context2, HashSet<Usuario> usuarios){
        this.usuarios=usuarios;
        context=context2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public FrameLayout card;
        public ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.nome);
            card = v.findViewById(R.id.card);
        }
    }

    public RecyclerViewAdapterUsuariosTodos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_usuarios, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        Aluno[] alunos = usuarios.toArray(new Aluno[usuarios.size()]);
        holder.textView.setText(alunos[position].getNome());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogConvidaUsuario.todos.remove(alunos[position]);
                PopupDialogConvidaUsuario.convidados.add(alunos[position]);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Event").setValue(Math.random());
            }
        });

    }

    public int getItemCount(){
        return usuarios.size();
    }
}
