package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Local {
    private String idLocal, nome;

    public Local() {
    }

    public Local(String idLocal, String nome) {
        this.idLocal = idLocal;
        this.nome = nome;
    }

    public String getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(String idLocal) {
        this.idLocal = idLocal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public boolean verificadisponibilidade(Date dataInicio, Date dataFim){
        return false;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Locais").child(String.valueOf(this.getIdLocal())).setValue(this);
    }
}
