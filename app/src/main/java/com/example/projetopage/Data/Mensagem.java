package com.example.projetopage.Data;

import java.util.Date;

public class Mensagem implements Comparable<Mensagem>{
    private String idMensagem, nomeUsuario, idUsuario, texto;
    private Date data;

    public Mensagem() {
    }

    public Mensagem(String idMensagem, String nomeUsuario, String idUsuario, String texto, Date data) {
        this.idMensagem = idMensagem;
        this.nomeUsuario = nomeUsuario;
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.data = data;
    }

    public String getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(String idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Mensagem)) {
            return false;
        }
        Mensagem mensagem = (Mensagem) o;
        return getIdMensagem().equals(mensagem.getIdMensagem());
    }

    @Override
    public int compareTo(Mensagem mensagem) {
        if(getData().after(mensagem.getData())){
            return 1;
        }else {
            return -1;
        }
    }
}
