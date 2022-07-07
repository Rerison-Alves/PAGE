package com.example.projetopage.Data;


import androidx.annotation.Nullable;

import java.util.Date;

public abstract class Usuario {
    private String idUsuario, nome, email;
    private Date datanasc;

    public Usuario() {
    }

    public Usuario(String nome, String email, Date datanasc) {
        this.nome = nome;
        this.email = email;
        this.datanasc = datanasc;
    }
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idAluno) {
        this.idUsuario = idAluno;
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

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

}
