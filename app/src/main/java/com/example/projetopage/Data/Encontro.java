package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Encontro {
    private String idEncontro, idAgrupamento;
    private String tema;
    private String descricao;

    public Encontro() {
    }

    public Encontro(String idEncontro, String tema, String idAgrupamento, String descricao) {
        this.idEncontro = idEncontro;
        this.tema = tema;
        this.idAgrupamento=idAgrupamento;
        this.descricao = descricao;

    }

    public String getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(String idEncontro) {
        this.idEncontro = idEncontro;
    }

    public String getTema() {
        return tema;
    }

    public void setNome(String tema) {
        this.tema = tema;
    }

    public String getIdAgrupamento() {
        return idAgrupamento;
    }

    public void setIdAgrupamento(String idAgrupamento) {
        this.idAgrupamento = idAgrupamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(getIdAgrupamento())).child("Encontros")
                .child(String.valueOf(getIdEncontro())).setValue(this);
    }

    public void remove(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(getIdAgrupamento())).child("Encontros")
                .child(String.valueOf(getIdEncontro())).removeValue();
    }
}
