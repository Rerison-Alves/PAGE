package com.example.projetopage.Data;

public class Grupo {
    public String nome;
    public Grupo(String nome){
        this.nome=nome;
    }

    public Grupo(int ind){
        this.nome= String.valueOf(ind);
    }
}
