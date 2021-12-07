package com.sc.ballot.entity;


public class KafkaMsg {
    private String mensagem;
    private Object valor;

    public KafkaMsg() {
    }

    public KafkaMsg(String mensagem, String valor) {
        this.mensagem = mensagem;
        this.valor = valor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
}
