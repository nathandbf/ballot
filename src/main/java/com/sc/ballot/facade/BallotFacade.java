package com.sc.ballot.facade;

import com.sc.ballot.controller.BallotController;
import com.sc.ballot.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BallotFacade {

    @Autowired
    private BallotController ballotController;

    /**
     * Método responsavel por unificar os métodos do fluxo Cadastrar Pauta;
     * @param nome Nome da nova Pauta
     * @param idUsuario ID do usuario que solicitou o cadastro (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    public Response cadastrarPauta(String nome, String idUsuario) {
        //TODO LOG DE ENTRADA
        return ballotController.cadastrarPauta(nome,idUsuario);
    }
}
