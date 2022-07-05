package com.example.projetopage.Data;

import java.util.Date;

public class Aluno extends Usuario{
    private String idAluno, curso, matricula;

    public Aluno() {
    }

    public Aluno(String nome, String email, String senha, Date datanasc, String curso, String matricula) {
        super(nome, email, senha, datanasc);
        this.curso = curso;
        this.matricula = matricula;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
