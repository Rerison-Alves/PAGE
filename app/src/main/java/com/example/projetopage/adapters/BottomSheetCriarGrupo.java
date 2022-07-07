package com.example.projetopage.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetopage.Data.Grupo;
import com.example.projetopage.Data.Usuario;
import com.example.projetopage.Data.UsuarioAgrupamento;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.example.projetopage.util.UsuarioAutenticado;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BottomSheetCriarGrupo extends BottomSheetDialogFragment {
    public BottomSheetCriarGrupo() {
    }
    Grupo novogrupo;
    EditText nomedogrupo, areadeestudo, descricao;
    Spinner spinner;
    ArrayList<Usuario> listadeconvidados;
    FrameLayout btn_convidaalunos, btn_concluir;
    private DatabaseReference myRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criargrupo, container, false);
        nomedogrupo = (EditText) view.findViewById(R.id.nomealuno);
        areadeestudo = (EditText) view.findViewById(R.id.areadeestudo);
        descricao = (EditText) view.findViewById(R.id.descricao);
        btn_convidaalunos= (FrameLayout) view.findViewById(R.id.btn_convidaalunos);
        TextView nconvidados = view.findViewById(R.id.nconvidados);
        btn_convidaalunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.convidaUsuario(getParentFragmentManager());
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
                if(!nomedogrupo.getText().toString().equals("")
                        &&!areadeestudo.getText().toString().equals("")
                        &&!nconvidados.getText().toString().equals("0/50")){
                novogrupo = new Grupo(
                        MainActivity.genUUI(),
                        nomedogrupo.getText().toString(),
                        descricao.getText().toString(),
                        spinner.getSelectedItem().toString(),
                        areadeestudo.getText().toString());
                convidarusuarios();
                novogrupo.salvar();
                dismiss();}else{
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inicializarfirebase();
        myRef.child("Event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nconvidados.setText(PopupDialogConvidaUsuario.convidados.size() + "/50");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void convidarusuarios() {
        UsuarioAgrupamento admin = new UsuarioAgrupamento(
                MainActivity.genUUI(),
                UsuarioAutenticado.UsuarioLogado().getUid(),
                novogrupo.getIdAgrupamento(),
                true);
        admin.salvar();
        for(Usuario usuario:PopupDialogConvidaUsuario.convidados){
            UsuarioAgrupamento membro = new UsuarioAgrupamento(
                    MainActivity.genUUI(),
                    usuario.getIdUsuario(),
                    novogrupo.getIdAgrupamento(),
                    false);
            membro.salvar();
        }
    }

    public void inicializarfirebase(){
        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }


    @Override
    public void onStop() {
        super.onStop();
        PopupDialogConvidaUsuario.LimparListas();
    }
}
