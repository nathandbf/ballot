package com.sc.ballot.dao;

import com.sc.ballot.entity.Pauta;

import java.util.List;

public interface BallotDAO {
    /**
     * Método responsavel por cadastrar a pauta na persistencia
     * @param pauta O objeto pauta a ser salvo
     * @return O Objeto Pauta Salvo
     */
    Pauta cadastrarPauta(Pauta pauta);

    /**
     * Método responsavel por procurar uma lista de pautas pelo nome e por um status.
     * @param nome nome das pautas a serem procuradas
     * @param status status da pauta
     * @return Lista de pautas correspondentes ao filtro solicitado
     */
    List<Pauta> buscarPautasPorNomeStatus(String nome, Integer status);

}
