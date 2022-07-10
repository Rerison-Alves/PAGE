package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.concurrent.TimeUnit;

public class RecyclerViewAdapterEncontrosAdm extends RecyclerView.Adapter<RecyclerViewAdapterEncontrosAdm.ViewHolder> {
    ArrayList<Encontro> encontros;
    Context context;
    View view;
    ViewHolder viewHolder;
    FragmentManager fragmentManager;

    public RecyclerViewAdapterEncontrosAdm(Context context, FragmentManager fragmentManager, ArrayList<Encontro> encontros){
        this.encontros = encontros;
        this.fragmentManager=fragmentManager;
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
            local = v.findViewById(R.id.local);
            edit = v.findViewById(R.id.edit);
            excluir= v.findViewById(R.id.excluir);
            consulta=v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterEncontrosAdm.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_encontros_adm, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomeDoEncontro.setText(encontros.get(position).getTema());
//        holder.data.setText(encontros.get(position).getAreadeEstudo());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaEncontro(encontros.get(position), context, fragmentManager);
            }
        });
        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.deletaEncontro(encontros.get(position), context);
            }
        });

        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("Agendamentos");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Agendamento agendamento = objsnapshot.getValue(Agendamento.class);
                    if(encontros.get(position).getIdEncontro().equals(agendamento.getIdEncontro())){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatahorario = new SimpleDateFormat("HH:mm");
                        Date datainicio=agendamento.getDataInicio(), datafim=agendamento.getDataFim();
                        String textodata = formatadata.format(datainicio)+" "+formatahorario.format(datainicio)+"h - "+formatahorario.format(datafim)+"h";
                        holder.data.setText(textodata);
                        holder.local.setText(agendamento.getIdLocal());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public int getItemCount(){
        return encontros.size();
    }
}
