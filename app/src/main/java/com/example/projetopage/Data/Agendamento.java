package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Agendamento {
    private String idAgendamento, idEncontro, idLocal;
    private Date dataInicio, dataFim;

    public Agendamento() {
    }

    public Agendamento(String idAgendamento, String idEncontro, String idLocal, Date dataInicio, Date dataFim) {
        this.idAgendamento = idAgendamento;
        this.idEncontro = idEncontro;
        this.idLocal = idLocal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(String idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(String idEncontro) {
        this.idEncontro = idEncontro;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agendamentos").child(String.valueOf(this.getIdAgendamento())).setValue(this);
    }

    public void remove(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agendamentos").child(String.valueOf(this.getIdAgendamento())).removeValue();
    }
}

