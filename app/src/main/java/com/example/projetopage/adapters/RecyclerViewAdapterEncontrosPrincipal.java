package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

public class RecyclerViewAdapterEncontrosPrincipal extends RecyclerView.Adapter<RecyclerViewAdapterEncontrosPrincipal.ViewHolder> {
    ArrayList<Encontro> Encontros;
    FragmentManager fragmentManager;
    Context context;
    View view;
    ViewHolder viewHolder;
    TextView textView;

    public RecyclerViewAdapterEncontrosPrincipal(Context context, FragmentManager fragmentManager, ArrayList<Encontro> Encontros){
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.Encontros=Encontros;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Tema, desc;
        LinearLayout consulta;
        public ViewHolder(View v){
            super(v);
            Tema = v.findViewById(R.id.textviewPrincipal);
            desc = v.findViewById(R.id.desc);
            consulta = v.findViewById(R.id.consulta);
        }
    }

    public RecyclerViewAdapterEncontrosPrincipal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    view = LayoutInflater.from(context).inflate(R.layout.card_grupos, parent, false);
    viewHolder = new ViewHolder(view);
    return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        holder.Tema.setText(Encontros.get(position).getTema());

        holder.consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.consultaEncontro(Encontros.get(position), context, fragmentManager);
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
                    if(agendamento.getIdEncontro().equals(Encontros.get(position).getIdEncontro())){
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatahorario = new SimpleDateFormat("HH:mm");
                        Date datainicio=agendamento.getDataInicio(), datafim=agendamento.getDataFim();
                        if(agendamento.getIdLocal().equals("Online")){
                            String textodata = formatadata.format(datainicio)+
                                    " "+formatahorario.format(datainicio)+
                                    "h - "+formatahorario.format(datafim)+"h "+
                                    agendamento.getIdLocal();
                            holder.desc.setText(textodata);
                        }else {
//                           Local.setText();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int getItemCount(){
        return Encontros.size();
    }
}
