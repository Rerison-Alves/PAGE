package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;

import java.util.ArrayList;

public class RecyclerViewAdapterExtended extends RecyclerView.Adapter<RecyclerViewAdapterExtended.ViewHolder> {
    ArrayList<Grupo> grupos;
    Context context;
    View view;
    ViewHolder viewHolder;
    FragmentManager fragmentManager;

    public RecyclerViewAdapterExtended(Context context, FragmentManager fragmentManager, ArrayList<Grupo> grupos){
        this.grupos = grupos;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public FrameLayout consulta;
        public ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.nomeDoGrupo);
            consulta=v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterExtended.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_extended, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(grupos.get(position).getNome());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaGrupo(grupos.get(position), context, fragmentManager);
            }
        });
    }

    public int getItemCount(){
        return grupos.size();
    }
}
