package com.example.projetopage.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    String[] valores;
    Context context;
    View view;
    ViewHolder viewHolder;
    TextView textView;

    public RecyclerViewAdapter(Context context2, String[] valores2){

        valores=valores2;
        context=context2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.nomeDoGrupo);
        }
    }

    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(valores[position]);
    }

    public int getItemCount(){
        return valores.length;
    }
}
