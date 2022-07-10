package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Agendamento;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Local;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapterLocais extends RecyclerView.Adapter<RecyclerViewAdapterLocais.ViewHolder> {
    ArrayList<Local> locais;
    Context context;
    View view;
    ViewHolder viewHolder;
    FragmentManager fragmentManager;

    public RecyclerViewAdapterLocais(Context context, FragmentManager fragmentManager, ArrayList<Local> locais){
        this.locais = locais;
        this.fragmentManager=fragmentManager;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeDoLocal;
        public FrameLayout escolher;
        public ViewHolder(View v){
            super(v);
            nomeDoLocal = v.findViewById(R.id.nomeLocal);
            escolher=v.findViewById(R.id.escolher);
        }
    }

    public RecyclerViewAdapterLocais.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_local, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomeDoLocal.setText(locais.get(position).getNome());

        holder.escolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupDialogEscolherLocal.localselecionado=locais.get(position);
                Intent intent = new Intent("atualizar_campos");
                context.sendBroadcast(intent);
            }
        });
    }


    public int getItemCount(){
        return locais.size();
    }
}
