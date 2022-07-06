package com.example.projetopage.Data;

import java.util.ArrayList;

public class Turma extends Agrupamento{
    String disciplina;
    String Turno;

    public Turma(String idAgrupamento, String nome, String descricao, String curso, String disciplina, String turno) {
        super(idAgrupamento, nome, descricao, curso);
        this.disciplina = disciplina;
        if(turno.equals("Manhã") || turno.equals("Tarde") || turno.equals("Noite")) {Turno = turno;}
        else{Turno="Indefinido";}
    }
}
