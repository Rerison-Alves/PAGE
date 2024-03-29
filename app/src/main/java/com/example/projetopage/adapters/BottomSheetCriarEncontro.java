package com.example.projetopage.adapters;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.projetopage.Data.Agendamento;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Local;
import com.example.projetopage.MainActivity;
import com.example.projetopage.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class BottomSheetCriarEncontro extends BottomSheetDialogFragment {
    public BottomSheetCriarEncontro() {
    }
    Context context;
    TextView localselecionado;
    EditText temaDoEncontro, data, descricao;
    MaskedEditText inicio , fim;
    FrameLayout btn_escolherlocal, btn_concluir;
    String idAgrupamento;

    public void show(@NonNull FragmentManager manager, @Nullable String tag, String idAgrupamento) {
        super.show(manager, tag);
        this.idAgrupamento=idAgrupamento;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.criarencontro, container, false);
        context = getContext();
        temaDoEncontro = (EditText) view.findViewById(R.id.nomealuno);
        data = (EditText) view.findViewById(R.id.data);
        inicio = (MaskedEditText) view.findViewById(R.id.datainicio);
        fim = (MaskedEditText) view.findViewById(R.id.datafim);
        localselecionado = (TextView) view.findViewById(R.id.localselecionado);
        descricao = (EditText) view.findViewById(R.id.descricao);
        btn_escolherlocal= (FrameLayout) view.findViewById(R.id.btn_escolherlocal);
        btn_escolherlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.escolherlocal(getActivity().getSupportFragmentManager());
            }
        });

        btn_concluir= (FrameLayout) view.findViewById(R.id.btn_concluir);
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Verificadados()){
                    Encontro encontro = new Encontro(
                            MainActivity.genUUI(),
                            temaDoEncontro.getText().toString(),
                            idAgrupamento,
                            descricao.getText().toString());
                    encontro.salvar();
                    Date datainicio=null, datafim=null;
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formata = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    try {
                        datainicio=formata.parse(inicio.getText()+" "+data.getText().toString());
                        datafim=formata.parse(fim.getText()+" "+data.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Agendamento agendamento = new Agendamento(MainActivity.genUUI(),encontro.getIdEncontro(), getlocal(), datainicio, datafim);
                    agendamento.salvar();
                    dismiss();
                }

            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("atualizar_campos")) {
                    localselecionado.setText(PopupDialogEscolherLocal.localselecionado.getNome());
                }
            }
        };
        getContext().registerReceiver(broadcastReceiver, new IntentFilter("atualizar_campos"));
        return view;
    }

    private String getlocal() {
        if(PopupDialogEscolherLocal.localselecionado!=null){
            return PopupDialogEscolherLocal.localselecionado.getNome();
        }else {
            return "Online";
        }

    }

    private boolean Verificadados() {
        boolean verificadados=true;
        //verifica obrigatorios
        if(temaDoEncontro.getText().toString().equals("")||data.getText().toString().equals("")
        ||inicio.getRawText().toString().equals("")||fim.getRawText().toString().equals("")){
            verificadados=false;
            Toast.makeText(context, "Campos obrigatórios não preenchidos!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Data é inválida!", Toast.LENGTH_SHORT).show();
            }
            //verifica horario
            Integer comeco = Integer.valueOf(inicio.getRawText().toString());
            Integer finalt = Integer.valueOf(fim.getRawText().toString());
            if(finalt-comeco>400||finalt-comeco<1){
                verificadados=false;
                Toast.makeText(context, "Duração máxima de 4 horas e apenas no mesmo dia!", Toast.LENGTH_SHORT).show();
            }
            if(comeco>2359||finalt>2359){
                verificadados=false;
                Toast.makeText(context, "Horários inválidos!", Toast.LENGTH_SHORT).show();
            }

        }
        return verificadados;
    }

    @Override
    public void onStop() {
        super.onStop();
        PopupDialogEscolherLocal.localselecionado=new Local();
    }
}
