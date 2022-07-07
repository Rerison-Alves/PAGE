package com.example.projetopage.Data;

import java.util.Date;
import java.util.Objects;

public class Aluno extends Usuario{
    private String curso, matricula;
    public Aluno() {
    }

    public Aluno(String nome, String email, Date datanasc, String curso, String matricula) {
        super(nome, email, datanasc);
        this.curso = curso;
        this.matricula = matricula;
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

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario());
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Aluno user = (Aluno) o;
        return getIdUsuario().equals(user.getIdUsuario());
    }
}
