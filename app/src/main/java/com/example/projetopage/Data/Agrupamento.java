package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Agrupamento {
    private int idAgrupamento;
    private String nome;
    private String descricao;
    private String curso;
    private ArrayList<Encontro> encontros;


    public Agrupamento() {
    }

    public Agrupamento(int idAgrupamento, String nome, String descricao, String curso) {
        this.idAgrupamento = idAgrupamento;
        this.nome = nome;
        this.curso = curso;
        this.descricao = descricao;
    }

    public int getIdAgrupamento() {
        return idAgrupamento;
    }

    public void setIdAgrupamento(int idAgrupamento) {
        this.idAgrupamento = idAgrupamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public ArrayList<Encontro> getEncontros() {
        return encontros;
    }

    public void setEncontros(ArrayList<Encontro> encontros) {
        this.encontros = encontros;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(this.getIdAgrupamento())).setValue(this);
    }

    public void remove(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(this.getIdAgrupamento())).removeValue();
    }
}
