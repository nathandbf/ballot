package com.sc.ballot.facade;

import com.sc.ballot.constant.ConstanteMsgLog;
import com.sc.ballot.controller.BallotController;
import com.sc.ballot.entity.GenericResponse;
import com.sc.ballot.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BallotFacade {
    Logger logger = LoggerFactory.getLogger(BallotFacade.class);
    @Autowired
    private BallotController ballotController;

    /**
     * Método responsavel por unificar os métodos do fluxo Cadastrar Pauta;
     * @param nome Nome da nova Pauta
     * @param idUsuario ID do usuario que solicitou o cadastro
     * @return Objeto Response com o código e status da operação
     */
    public Response cadastrarPauta(String nome, String idUsuario) {
        String param = "nome:" +nome;
        logger.info(ConstanteMsgLog.INICIO_FLUXO.formatted(idUsuario,"cadastrarPauta",param));
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
        String param = "idPauta:" +idPauta + " tempoSegundos:" +tempoSegundos;
        logger.info(ConstanteMsgLog.INICIO_FLUXO.formatted(idUsuario,"abrirPauta",param));
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
        String param = "idPauta:" +idPauta + " voto:" +voto;
        logger.info(ConstanteMsgLog.INICIO_FLUXO.formatted(idUsuario,"votar",param));
        return ballotController.votar(voto, idPauta, idUsuario);
    }

    /**
     * Método responsavel por unificar os métodos do fluxo de obter resultados de uma votação
     * @param idPauta Id da pauta a ser consultada
     * @param idUsuario  ID do usuario que solicitou a consulta
     * @return
     */
    public Response getResultadosVotacao(Integer idPauta, String  idUsuario) {
        String param = "idPauta:" +idPauta;
        logger.info(ConstanteMsgLog.INICIO_FLUXO.formatted(idUsuario,"getResultadosVotacao",param));
        return ballotController.getResultadosVotacao(idPauta,idUsuario);
    }
}
