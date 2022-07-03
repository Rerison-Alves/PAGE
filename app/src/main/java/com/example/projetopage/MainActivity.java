package com.example.projetopage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projetopage.Data.Agrupamento;
import com.example.projetopage.Data.Encontro;
import com.example.projetopage.Data.Grupo;
import com.example.projetopage.adapters.BottomSheetCadastro;
import com.example.projetopage.adapters.BottomSheetCadastroAluno;
import com.example.projetopage.adapters.BottomSheetCriarEncontro;
import com.example.projetopage.adapters.BottomSheetCriarGrupo;
import com.example.projetopage.adapters.GrupoDialog;
import com.example.projetopage.adapters.PopupDialogConvidaUsuario;

public class MainActivity extends AppCompatActivity {


    private ViewGroup rootLayout;
    int prevX,prevY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView cima = (ImageView) findViewById(R.id.btn_cima);

        cima.setOnTouchListener(new ChoiceTouchListener());

    }
    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(final View v,final MotionEvent event)
        {
            final FrameLayout.LayoutParams par=(FrameLayout.LayoutParams)v.getLayoutParams();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_MOVE:
                {
                    prevY=(int)event.getRawY();
                    prevX=(int)event.getRawX();
                    v.setLayoutParams(par);
                    par.topMargin+=(int)event.getRawY()-prevY;
                    return true;
                }
                case MotionEvent.ACTION_UP:
                {
//                    abrirtela2(v);
                    bottomsheetcadastro();
                    par.topMargin+=(int)event.getRawY()-prevY;
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_DOWN:
                {
                    prevX=(int)event.getRawX();
                    prevY=(int)event.getRawY();
                    par.bottomMargin=v.getHeight();
                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    }
    //teste2
    public void abrirtela2(View v){
        Intent ittela2 = new Intent(this,AbaPrincipal.class);
        startActivity(ittela2);
    }

    public void bottomsheetcadastro(){
        BottomSheetCadastro bottomSheetCadastro = new BottomSheetCadastro();
        bottomSheetCadastro.show(getSupportFragmentManager(), "TAG");
    }
    static public void cadastroaluno(FragmentManager fragmentManager){
        BottomSheetCadastroAluno bottomSheetCadastroAluno = new BottomSheetCadastroAluno();
        bottomSheetCadastroAluno.show(fragmentManager, "TAG");
    }
    static public void bottomsheetcriargrupo(FragmentManager fragmentManager){
        BottomSheetCriarGrupo bottomSheetCriarGrupo = new BottomSheetCriarGrupo();
        bottomSheetCriarGrupo.show(fragmentManager,"TAG");
    }
    static public void bottomsheetcriarencontro(FragmentManager fragmentManager, int idAgrupamento){
        BottomSheetCriarEncontro bottomSheetCriarEncontro = new BottomSheetCriarEncontro();
        bottomSheetCriarEncontro.show(fragmentManager,"TAG", idAgrupamento);
    }
    static public void convidaUsuario(FragmentManager fragmentManager){
        PopupDialogConvidaUsuario popupDialogConvidaUsuario = new PopupDialogConvidaUsuario();
        popupDialogConvidaUsuario.show(fragmentManager, "TAG");
    }

    static public void consultaGrupo(Grupo grupo, Context context, FragmentManager fragmentManager){
        GrupoDialog dialog = new GrupoDialog(grupo, context, fragmentManager, R.style.Theme_ProjetoPAGE);
        dialog.show();
    }
    static public void consultaEncontro(Encontro encontro, Context context){

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
    static public void deletaEncontro(Encontro encontro, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Ao fazer isso seu encontro será excluido permanentemente!").setTitle("Deseja excluir "+ encontro.getNome()+ "?");
        builder.setNegativeButton(R.string.sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                encontro.remove();
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