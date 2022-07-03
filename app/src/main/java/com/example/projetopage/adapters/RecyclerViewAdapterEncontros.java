package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;

import java.util.ArrayList;

public class RecyclerViewAdapterEncontros extends RecyclerView.Adapter<RecyclerViewAdapterEncontros.ViewHolder> {
    ArrayList<Encontro> encontros;
    Context context;
    View view;
    ViewHolder viewHolder;

    public RecyclerViewAdapterEncontros(Context context, ArrayList<Encontro> encontros){
        this.encontros = encontros;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeDoEncontro, data, local;
        public ImageButton edit, excluir;
        public FrameLayout consulta;
        public ViewHolder(View v){
            super(v);
            nomeDoEncontro = v.findViewById(R.id.nomeDoEncontro);
            data = v.findViewById(R.id.data);
            edit = v.findViewById(R.id.edit);
            excluir= v.findViewById(R.id.excluir);
            consulta=v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterEncontros.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_encontros, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomeDoEncontro.setText(encontros.get(position).getNome());
//        holder.data.setText(encontros.get(position).getAreadeEstudo());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.deletaEncontro(encontros.get(position), context);
            }
        });

    }

    public int getItemCount(){
        return encontros.size();
    }
}
