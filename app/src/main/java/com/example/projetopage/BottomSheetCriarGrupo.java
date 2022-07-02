package com.example.projetopage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.Usuario;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetCriarGrupo extends BottomSheetDialogFragment {
    public BottomSheetCriarGrupo() {
    }
    EditText nomedogrupo, areadeestudo, descricao;
    Spinner spinner;
    ArrayList<Usuario> listadeconvidados;
    FrameLayout btn_convidaalunos, btn_concluir;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criargrupo, container, false);
        nomedogrupo = (EditText) view.findViewById(R.id.nomedogrupo);
        areadeestudo = (EditText) view.findViewById(R.id.areadeestudo);
        descricao = (EditText) view.findViewById(R.id.descricao);
        btn_convidaalunos= (FrameLayout) view.findViewById(R.id.btn_convidaalunos);
        btn_convidaalunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentdialog(getActivity().getSupportFragmentManager());
            }
        });
        //Spinner
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.cursos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //
        btn_concluir= (FrameLayout) view.findViewById(R.id.btn_concluir);
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Grupo novogrupo = new Grupo((int)System.currentTimeMillis(),
                        nomedogrupo.getText().toString(),
                        descricao.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        areadeestudo.getText().toString());
                novogrupo.salvar();
                dismiss();
            }
        });
        return view;
    }

}
