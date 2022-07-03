package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Usuario;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class BottomSheetCriarEncontro extends BottomSheetDialogFragment {
    public BottomSheetCriarEncontro() {
    }
    EditText temaDoEncontro, data, descricao;
    MaskedEditText inicio , fim;
    FrameLayout btn_escolherlocal, btn_concluir;
    int idAgrupamento;

    public void show(@NonNull FragmentManager manager, @Nullable String tag, int idAgrupamento) {
        super.show(manager, tag);
        this.idAgrupamento=idAgrupamento;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criarencontro, container, false);
        Context context = getContext();
        temaDoEncontro = (EditText) view.findViewById(R.id.temadoEncontro);
        data = (EditText) view.findViewById(R.id.data);
        inicio = (MaskedEditText) view.findViewById(R.id.datainicio);
        fim = (MaskedEditText) view.findViewById(R.id.datafim);
        descricao = (EditText) view.findViewById(R.id.descricao);
        btn_escolherlocal= (FrameLayout) view.findViewById(R.id.btn_convidaalunos);
//        btn_escolherlocal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity.convidaUsuario(getActivity().getSupportFragmentManager());
//            }
//        });

        btn_concluir= (FrameLayout) view.findViewById(R.id.btn_concluir);
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verificadados=true;
                //verifica obrigatorios
                if(temaDoEncontro.getText().toString().equals("")||data.getText().toString().equals("")
                ||inicio.getRawText().toString().equals("")||fim.getRawText().toString().equals("")){
                    verificadados=false;
                    Toast.makeText(context, "Campos obrigatórios não preenchidos", Toast.LENGTH_SHORT).show();
                }else {
                    //verifica data
                    Date dataatual=new Date();
                    Date datateste= null;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formata = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    try {
                        datateste=formata.parse(inicio.getText()+" "+data.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dataatual.after(datateste)) {
                        verificadados=false;
                        Toast.makeText(context, "Data é inválida", Toast.LENGTH_SHORT).show();
                    }
                    //verifica horario
                    Integer comeco = Integer.valueOf(inicio.getRawText().toString());
                    Integer finalt = Integer.valueOf(fim.getRawText().toString());
                    if(finalt-comeco>400){
                        verificadados=false;
                        Toast.makeText(context, "Duração máxima de 4 horas", Toast.LENGTH_SHORT).show();
                    }

                }
                if (verificadados){
                    Encontro encontro = new Encontro((int)System.currentTimeMillis(),
                            temaDoEncontro.getText().toString(),
                            idAgrupamento,
                            descricao.getText().toString());
                    encontro.salvar();
                    dismiss();
                }

            }
        });
        return view;
    }

}
