package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Usuario;
import com.example.projetopage.R;

import java.util.ArrayList;

public class RecyclerViewAdapterUsuarios extends RecyclerView.Adapter<RecyclerViewAdapterUsuarios.ViewHolder> {
    ArrayList<Usuario> usuarios;
    Context context;
    View view;
    ViewHolder viewHolder;

    public RecyclerViewAdapterUsuarios(Context context2, ArrayList<Usuario> usuarios){
        this.usuarios=usuarios;
        context=context2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.nome);
        }
    }

    public RecyclerViewAdapterUsuarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_usuarios, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(usuarios.get(position).getNome());
    }

    public int getItemCount(){
        return usuarios.size();
    }
}
