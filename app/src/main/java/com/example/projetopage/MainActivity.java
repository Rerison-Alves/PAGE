package com.example.projetopage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projetopage.Data.Agrupamento;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cima = (Button) findViewById(R.id.btn_cima);

        //teste do banco
//        Grupo POO = new Grupo((int)System.currentTimeMillis(), "POO de amigo", "Alguma coisa legal",
//                "Ciência da comp", null, "Programação");
//        POO.salvar();
        //fim do teste

        cima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirtela2(view);
            }
        });

    }
    //teste2
    public void abrirtela2(View v){
        Intent ittela2 = new Intent(this,AbaPrincipal.class);
        startActivity(ittela2);
    }

    static public void bottomsheetdialog(FragmentManager fragmentManager){
        BottomSheetCriarGrupo bottomSheetCriarGrupo = new BottomSheetCriarGrupo();
        bottomSheetCriarGrupo.show(fragmentManager,"TAG");
    }

    static public void convidaUsuario(FragmentManager fragmentManager){
        PopupDialogConvidaUsuario popupDialogConvidaUsuario = new PopupDialogConvidaUsuario();
        popupDialogConvidaUsuario.show(fragmentManager, "TAG");
    }

    static public void deletaGrupo(Agrupamento agrupamento, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Ao fazer isso seu grupo/turma será excluido permanentemente!").setTitle("Deseja excluir "+ agrupamento.getNome()+ "?");
        builder.setNegativeButton(R.string.sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                agrupamento.remove();
            }
        });
        builder.setPositiveButton(R.string.nao, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}