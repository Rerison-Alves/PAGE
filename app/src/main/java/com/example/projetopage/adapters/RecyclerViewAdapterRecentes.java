package com.example.projetopage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Encontro;
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

public class RecyclerViewAdapterRecentes extends RecyclerView.Adapter<RecyclerViewAdapterRecentes.ViewHolder> {
    ArrayList<Grupo> Grupos;
    FragmentManager fragmentManager;
    Context context;
    View view;
    ViewHolder viewHolder;

    public RecyclerViewAdapterRecentes(Context context, FragmentManager fragmentManager, ArrayList<Grupo> Grupos){
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.Grupos=Grupos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeDoGrupo, desc;
        LinearLayout consulta;
        public ViewHolder(View v){
            super(v);
            nomeDoGrupo = v.findViewById(R.id.textviewPrincipal);
            desc = v.findViewById(R.id.desc);
            consulta = v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterRecentes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.nomeDoGrupo.setText(Grupos.get(position).getNome());
        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaGrupo(Grupos.get(position), context, fragmentManager);
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
                    if(Grupos.get(position).getIdAgrupamento().equals(usuarioAgrupamento.getIdAgrupmaneto())){
                        list.add(usuarioAgrupamento);
                    }
                }
                String textoparticipantes = list.size()+" participantes";
                holder.desc.setText(textoparticipantes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int getItemCount(){
        return Grupos.size();
    }
}
