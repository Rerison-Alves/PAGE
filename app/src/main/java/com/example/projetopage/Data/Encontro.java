package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Encontro {
    private int idEncontro;
    private String nome;

    public Encontro() {
    }

    public Encontro(String nome) {
        this.idEncontro = (int)System.currentTimeMillis();
        this.nome = nome;
    }

    public int getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(int idEncontro) {
        this.idEncontro = idEncontro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference agrupamentos = reference.child("usuarios");

    public void salvar(){
        agrupamentos.child(String.valueOf(idEncontro)).setValue(this);
    }
}
