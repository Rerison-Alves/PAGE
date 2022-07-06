package com.example.projetopage.adapters;

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

import com.example.projetopage.Data.Aluno;
import com.example.projetopage.Data.Usuario;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.example.projetopage.adapters.RecyclerViewAdapterExtended;
import com.example.projetopage.util.UsuarioAutenticado;

import java.util.ArrayList;

public class PopupDialogConvidaUsuario extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    RecyclerView ListaConvidados, ListaTodos;
    Context context;
    RecyclerViewAdapterUsuarios recyclerViewAdapterUsuarios;
    Usuario aluno=new Aluno(), aluno2=new Aluno();
    ArrayList<Usuario> todos = new ArrayList<Usuario>();
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
                dismiss();
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ListaConvidados = (RecyclerView) view.findViewById(R.id.ListaConvidados);
        ListaTodos = (RecyclerView) view.findViewById(R.id.ListaTodos);
        aluno.setNome(UsuarioAutenticado.UsuarioLogado().getDisplayName());
        aluno2.setNome("Teste aluno");
        todos.add(aluno);
        todos.add(aluno2);

        recyclerViewAdapterUsuarios = new RecyclerViewAdapterUsuarios(context, todos);

        RecyclerView.LayoutManager recycleLayoutConvidados = new LinearLayoutManager(context) ;
        RecyclerView.LayoutManager recycleLayoutTodos = new LinearLayoutManager(context) ;

        ListaConvidados.setLayoutManager(recycleLayoutConvidados);
        ListaConvidados.setAdapter(recyclerViewAdapterUsuarios);

        ListaTodos.setLayoutManager(recycleLayoutTodos);
        ListaTodos.setAdapter(recyclerViewAdapterUsuarios);
        return builder.create();
    }

}