package com.example.projetopage.Data;

import java.io.Serializable;

public class Usuario implements Comparable<Usuario>, Serializable {
    String nome;

    public Usuario() {
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Usuario usuario) {
        int i = nome.compareTo(usuario.nome);
        return i;
    }
}
