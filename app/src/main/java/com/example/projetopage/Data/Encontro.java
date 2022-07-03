package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Encontro {
    private int idEncontro, idAgrupamento;
    private String tema;
    private String descricao;

    public Encontro() {
    }

    public Encontro(int idEncontro, String tema, int idAgrupamento, String descricao) {
        this.idEncontro = idEncontro;
        this.tema = tema;
        this.idAgrupamento=idAgrupamento;
        this.descricao = descricao;

    }

    public int getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(int idEncontro) {
        this.idEncontro = idEncontro;
    }

    public String getNome() {
        return tema;
    }

    public void setNome(String nome) {
        this.tema = nome;
    }

    public int getIdAgrupamento() {
        return idAgrupamento;
    }

    public void setIdAgrupamento(int idAgrupamento) {
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
