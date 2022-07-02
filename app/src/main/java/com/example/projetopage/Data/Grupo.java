package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Grupo extends Agrupamento{
    private String areadeEstudo;

    public Grupo() {
    }

    public Grupo(int idAgrupamento, String nome, String descricao, String curso, String areadeEstudo) {
        super(idAgrupamento, nome, descricao, curso);
        this.areadeEstudo = areadeEstudo;
    }

    public String getAreadeEstudo() {
        return areadeEstudo;
    }

    public void setAreadeEstudo(String areadeEstudo) {
        this.areadeEstudo = areadeEstudo;
    }

    @Override
    public void salvar() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(this.getIdAgrupamento())).setValue(this);
    }
}
