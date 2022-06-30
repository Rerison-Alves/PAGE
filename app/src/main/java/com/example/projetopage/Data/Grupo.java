package com.example.projetopage.Data;

import java.util.ArrayList;

public class Grupo extends Agrupamento{
    String areadeEstudo;

    public Grupo() {
    }

    public Grupo(int idAgrupamento, String nome, String descricao, String curso, Usuario[] membros, String areadeEstudo) {
        super(idAgrupamento, nome, descricao, curso, membros);
        this.areadeEstudo = areadeEstudo;
    }

    public String getAreadeEstudo() {
        return areadeEstudo;
    }

    public void setAreadeEstudo(String areadeEstudo) {
        this.areadeEstudo = areadeEstudo;
    }
}
