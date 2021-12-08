package com.sc.ballot.entity;


public class VotacaoResult implements Response {
    private String nome;
    private int votosSim;
    private int votosNao;
    private int duracaoSegundos;
    private String status;
    private String vencedor;

    public VotacaoResult() {
    }

    public VotacaoResult(String nome, int votosSim, int votosNao, int duracaoSegundos, String status) {
        this.nome = nome;
        this.votosSim = votosSim;
        this.votosNao = votosNao;
        this.duracaoSegundos = duracaoSegundos;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVotosSim() {
        return votosSim;
    }

    public void setVotosSim(int votosSim) {
        this.votosSim = votosSim;
    }

    public int getVotosNao() {
        return votosNao;
    }

    public void setVotosNao(int votosNao) {
        this.votosNao = votosNao;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
}
