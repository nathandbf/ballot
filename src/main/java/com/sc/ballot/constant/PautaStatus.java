package com.sc.ballot.constant;

public enum PautaStatus {
    NAO_INICIALIZADO(0), ANDAMENTO(1), ENCERRADO(2);
    private int status;

    PautaStatus (int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
