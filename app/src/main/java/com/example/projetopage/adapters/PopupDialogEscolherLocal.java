package com.example.projetopage.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Local;
import com.example.projetopage.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PopupDialogEscolherLocal extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    Context context;
    private DatabaseReference myRef;
    static Local localselecionado = new Local();
    ArrayList<Local> listalocais=new ArrayList<Local>();
    RecyclerViewAdapterLocais recyclerViewAdapterLocais;
    RecyclerView locais;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context= getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.escolherlocal,null);
        builder.setView(view).setTitle("Escolha um Local (Online por padrão):").setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("atualizar_campos")) {
                    dismiss();
                }
            }
        };
        getContext().registerReceiver(broadcastReceiver, new IntentFilter("atualizar_campos"));
        inicializarfirebase();
        consultalocais();
        locais = (RecyclerView) view.findViewById(R.id.locais);
        setLocais();
        return builder.create();
    }

    private void setLocais() {
        RecyclerView.LayoutManager recycleLayoutUsuario = new LinearLayoutManager(context) ;
        locais.setLayoutManager(recycleLayoutUsuario);
        recyclerViewAdapterLocais = new RecyclerViewAdapterLocais(context, getParentFragmentManager() ,listalocais);
        locais.setAdapter(recyclerViewAdapterLocais);
    }

    private void consultalocais() {
        Query query = myRef.child("Locais").orderByChild("nome");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listalocais.clear();
                for (DataSnapshot objsnapshot:snapshot.getChildren()){
                    Local local = objsnapshot.getValue(Local.class);
                    listalocais.add(local);
                }
                setLocais();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void inicializarfirebase(){
        FirebaseApp.initializeApp(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

}
