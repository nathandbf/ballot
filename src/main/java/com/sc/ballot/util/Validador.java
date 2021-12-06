package com.sc.ballot.util;

public class Validador {

    /**
     * Método responsanvel por verificar se a String é nula ou branca/vazia.
     * @param nome
     * @return TRUE se é a String for valida, FALSE se não
     */
    public boolean validarString(String nome) {
        if(nome==null|| nome.isBlank()) {
            return false;
        }
        return true;
    }
}
