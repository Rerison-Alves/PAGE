package com.example.projetopage.Data;


import java.util.Date;

public abstract class Usuario {
    private String nome, email, senha;
    private Date datanasc;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, Date datanasc) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.datanasc = datanasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }
}
