package com.example.projetopage.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioAgrupamento {
    private String idUsuarioAgrupamento, idUsuario, idAgrupmaneto;
    boolean adm;

    public UsuarioAgrupamento(){}
    public UsuarioAgrupamento(String idUsuarioAgrupamento, String idUsuario, String idAgrupmaneto, boolean adm) {
        this.idUsuarioAgrupamento = idUsuarioAgrupamento;
        this.idUsuario = idUsuario;
        this.idAgrupmaneto = idAgrupmaneto;
        this.adm=adm;
    }

    public String getIdUsuarioAgrupamento() {
        return idUsuarioAgrupamento;
    }

    public void setIdUsuarioAgrupamento(String idUsuarioAgrupamento) {
        this.idUsuarioAgrupamento = idUsuarioAgrupamento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdAgrupmaneto() {
        return idAgrupmaneto;
    }

    public void setIdAgrupmaneto(String idAgrupmaneto) {
        this.idAgrupmaneto = idAgrupmaneto;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public void salvar(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("UsuarioAgrupamento").child(String.valueOf(this.getIdUsuarioAgrupamento())).setValue(this);
    }

    public void remove(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("UsuarioAgrupamento").child(String.valueOf(this.getIdUsuarioAgrupamento())).removeValue();
    }
}
