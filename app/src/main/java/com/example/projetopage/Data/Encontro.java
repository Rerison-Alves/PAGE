package com.example.projetopage.Data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Encontro {
    private String idEncontro, idAgrupamento;
    private String tema;
    private String descricao;

    public Encontro() {
    }

    public Encontro(String idEncontro, String tema, String idAgrupamento, String descricao) {
        this.idEncontro = idEncontro;
        this.tema = tema;
        this.idAgrupamento=idAgrupamento;
        this.descricao = descricao;

    }

    public String getIdEncontro() {
        return idEncontro;
    }

    public void setIdEncontro(String idEncontro) {
        this.idEncontro = idEncontro;
    }

    public String getTema() {
        return tema;
    }

    public void setNome(String tema) {
        this.tema = tema;
    }

    public String getIdAgrupamento() {
        return idAgrupamento;
    }

    public void setIdAgrupamento(String idAgrupamento) {
        this.idAgrupamento = idAgrupamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(getIdAgrupamento())).child("Encontros")
                .child(String.valueOf(getIdEncontro())).setValue(this);
    }

    public void remove(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Agrupamentos").child(String.valueOf(getIdAgrupamento())).child("Encontros")
                .child(String.valueOf(getIdEncontro())).removeValue();
        Query query = mDatabase.child("Agendamentos");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objsnapshot:snapshot.getChildren()){
                    Agendamento agendamento = objsnapshot.getValue(Agendamento.class);
                    if(agendamento.getIdEncontro().equals(getIdEncontro())){
                        mDatabase.child("Agendamentos").child(agendamento.getIdAgendamento()).removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int hashCode() {
        return getIdEncontro().hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Encontro)) {
            return false;
        }
        Encontro encontro = (Encontro) o;
        return this.getIdEncontro().equals(encontro.getIdEncontro());
    }


}
