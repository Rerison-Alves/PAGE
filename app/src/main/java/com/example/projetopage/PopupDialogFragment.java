package com.example.projetopage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetopage.Data.Usuario;
import com.example.projetopage.fragments.adapters.RecyclerViewAdapterExtended;

public class PopupDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    RecyclerView ListaConvidados, ListaTodos;
    Context context;
    RecyclerViewAdapterExtended recyclerViewAdapterExtended;
    String[] testeconvidados= new String[]{"Usuario 1", "Usuario 2"};
    String[] testetodos= new String[]{"Usuario 1", "Usuario 2", "Usuario 3"};
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context= getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.convidaalunos,null);
        builder.setView(view).setTitle("").
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ListaConvidados = (RecyclerView) view.findViewById(R.id.ListaConvidados);
        RecyclerView.LayoutManager recycleLayoutConvidados = new LinearLayoutManager(context) ;
        ListaConvidados.setLayoutManager(recycleLayoutConvidados);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, testeconvidados);
        ListaConvidados.setAdapter(recyclerViewAdapterExtended);

        ListaTodos = (RecyclerView) view.findViewById(R.id.ListaTodos);
        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context) ;
        ListaTodos.setLayoutManager(recycleLayoutTodos);
        recyclerViewAdapterExtended = new RecyclerViewAdapterExtended(context, testetodos);
        ListaTodos.setAdapter(recyclerViewAdapterExtended);
        return builder.create();
    }

}