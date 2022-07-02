package com.example.projetopage.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Agrupamento;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;

import java.util.ArrayList;

public class RecyclerViewAdapterPerfil extends RecyclerView.Adapter<RecyclerViewAdapterPerfil.ViewHolder> {
    ArrayList<Grupo> agrupamentos;
    Context context;
    View view;
    ViewHolder viewHolder;
    TextView nomedogrupo, areadogrupo;

    public RecyclerViewAdapterPerfil(Context context, ArrayList<Grupo> agrupamentos){
        this.agrupamentos=agrupamentos;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomedogrupo, areadogrupo;
        public ImageButton edit, excluir;
        public ViewHolder(View v){
            super(v);
            nomedogrupo = v.findViewById(R.id.nomeDoGrupo);
            areadogrupo = v.findViewById(R.id.areadogrupo);
            edit = v.findViewById(R.id.edit);
            excluir= v.findViewById(R.id.excluir);
        }
    }

    public RecyclerViewAdapterPerfil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_perfil, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomedogrupo.setText(agrupamentos.get(position).getNome());
        holder.areadogrupo.setText(agrupamentos.get(position).getAreadeEstudo());
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.deletaGrupo(agrupamentos.get(position), context);
            }
        });

    }

    public int getItemCount(){
        return agrupamentos.size();
    }
}
