package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        public TextView nomeDoGrupo, participantes;
        public FrameLayout consulta;
        public ViewHolder(View v){
            super(v);
            nomeDoGrupo = v.findViewById(R.id.textviewPrincipal);
            consulta=v.findViewById(R.id.consulta);
            participantes=v.findViewById(R.id.participantes);
        }
    }

    public RecyclerViewAdapterExtended.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos_extended, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomeDoGrupo.setText(grupos.get(position).getNome());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaGrupo(grupos.get(position), context, fragmentManager);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Query query = myRef.child("UsuarioAgrupamento");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<UsuarioAgrupamento> list = new ArrayList<UsuarioAgrupamento>();
                for(DataSnapshot objsnapshot:snapshot.getChildren()) {
                    UsuarioAgrupamento usuarioAgrupamento = objsnapshot.getValue(UsuarioAgrupamento.class);
                    if(grupos.get(position).getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                        list.add(usuarioAgrupamento);
                    }
                }
                String textoparticipantes = list.size()+" participantes";
                holder.participantes.setText(textoparticipantes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int getItemCount(){
        return grupos.size();
    }
}
