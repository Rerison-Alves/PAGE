package com.example.projetopage.Data;

import java.util.ArrayList;

public class Turma extends Agrupamento{
    String disciplina;
    String Turno;

    public Turma(int idAgrupamento, String nome, String descricao, String curso, Usuario[] membros, String disciplina, String turno) {
        super(idAgrupamento, nome, descricao, curso, membros);
        this.disciplina = disciplina;
        if(turno.equals("Manhã") || turno.equals("Tarde") || turno.equals("Noite")) {Turno = turno;}
        else{Turno="Idefinido";}
    }
}
