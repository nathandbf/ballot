package com.sc.ballot.facade;

import com.sc.ballot.controller.BallotController;
import com.sc.ballot.entity.GenericResponse;
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
     * @param idUsuario ID do usuario que solicitou o cadastro
     * @return Objeto Response com o código e status da operação
     */
    public Response cadastrarPauta(String nome, String idUsuario) {
        //TODO LOG DE ENTRADA
        return ballotController.cadastrarPauta(nome,idUsuario);
    }

    /**
     * Método responsavel por unificar os métodos do fluxo Abrir Pauta;
     * @param idPauta Id da pauta a ser iniciada
     * @param tempoSegundos tempo em segundos que a pauta deve ficar aberta
     * @param idUsuario ID do usuario que solicitou a abertura
     * @return Objeto Response com o código e status da operação
     */
    public Response abrirPauta(Integer idPauta, Integer tempoSegundos, String idUsuario) {
        //TODO LOG DE ENTRADA
        tempoSegundos = ballotController.verificarAlterarTempoSegundos(tempoSegundos);
        GenericResponse response = ballotController.abrirPauta(idPauta, tempoSegundos, idUsuario);
        if(response.getCode() == 200)
            ballotController.prepararFechamentoPauta(idPauta,tempoSegundos);
        return response;
    }

    /**
     * Método responsavel por unificar os métodos do fluxo votar
     * @param voto String "Sim" or "Não"
     * @param idPauta Id da pauta a ser iniciada
     * @param idUsuario  ID do usuario que solicitou o voto (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    public Response votar(String voto, Integer idPauta, String idUsuario) {
        return ballotController.votar(voto, idPauta, idUsuario);
    }

    /**
     * Método responsavel por unificar os métodos do fluxo de obter resultados de uma votação
     * @param idPauta Id da pauta a ser consultada
     * @param idUsuario  ID do usuario que solicitou a consulta
     * @return
     */
    public Response getResultadosVotacao(Integer idPauta, String  idUsuario) {
        return ballotController.getResultadosVotacao(idPauta,idUsuario);
    }
}
