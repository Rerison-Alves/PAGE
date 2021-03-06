package com.example.projetopage.Data;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public abstract class Agrupamento {
    private String idAgrupamento;
    private String nome;
    private String descricao;
    private String curso;
    private ArrayList<Encontro> encontros;


    public Agrupamento() {
    }

    public Agrupamento(String idAgrupamento, String nome, String descricao, String curso) {
        this.idAgrupamento = idAgrupamento;
        this.nome = nome;
        this.curso = curso;
        this.descricao = descricao;
    }

    public String getIdAgrupamento() {
        return idAgrupamento;
    }

    public void setIdAgrupamento(String idAgrupamento) {
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
