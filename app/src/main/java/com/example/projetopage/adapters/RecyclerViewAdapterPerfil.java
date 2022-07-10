package com.example.projetopage.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;

import java.util.ArrayList;

public class RecyclerViewAdapterPerfil extends RecyclerView.Adapter<RecyclerViewAdapterPerfil.ViewHolder> {
    ArrayList<Grupo> grupos;
    Context context;
    View view;
    ViewHolder viewHolder;
    FragmentManager fragmentManager;

    public RecyclerViewAdapterPerfil(Context context, FragmentManager fragmentManager,ArrayList<Grupo> grupos){
        this.grupos = grupos;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomedogrupo, areadogrupo;
        public ImageButton edit, excluir;
        public FrameLayout consulta;
        public ViewHolder(View v){
            super(v);
            nomedogrupo = v.findViewById(R.id.textviewPrincipal);
            areadogrupo = v.findViewById(R.id.areadogrupo);
            edit = v.findViewById(R.id.edit);
            excluir= v.findViewById(R.id.excluir);
            consulta=v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterPerfil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_perfil, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomedogrupo.setText(grupos.get(position).getNome());
        holder.areadogrupo.setText(grupos.get(position).getAreadeEstudo());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaGrupoPerfil(grupos.get(position), context, fragmentManager);
            }
        });
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.deletaGrupo(grupos.get(position), context);
            }
        });

    }

    public int getItemCount(){
        return grupos.size();
    }
}
