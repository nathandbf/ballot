package com.sc.ballot;

import com.sc.ballot.entity.Response;
import com.sc.ballot.facade.BallotFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Api {

    @Autowired
    private BallotFacade facade;

    /**
     * Teste Simples verificando se o sistema está online.
     * @return
     */
    @GetMapping("/v1/isOnline")
    public String isOnline() {
        return "Sim";
    }

    /**
     * Método que inicia o fluxo de cadastro de pauta, utilizando apenas o nome
     * @param nome Nome da nova Pauta
     * @param idUsuario ID do usuario que solicitou o cadastro (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    @PutMapping("/v1/cadastrarPauta")
    @ResponseBody
    public Response cadastrarPauta(String nome, String idUsuario){
        return facade.cadastrarPauta(nome,idUsuario);
    }

    /**
     * Método que inicia o fluxo de abertura de pauta
     * @param idPauta Id da pauta a ser iniciada
     * @param tempoSegundos tempo em segundos que a pauta deve ficar aberta
     * @param idUsuario ID do usuario que solicitou a abertura (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    @PostMapping("/v1/abrirPauta")
    @ResponseBody
    public Response abrirPauta(Integer idPauta, Integer tempoSegundos, String idUsuario){
        return facade.abrirPauta(idPauta,tempoSegundos,idUsuario);
    }

    /**
     * Método que inicia o fluxo de votação de uma pauta
     * @param voto String "Sim" or "Não"
     * @param idPauta Id da pauta a ser votada
     * @param idUsuario  ID do usuario que solicitou o voto (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    @PostMapping("/v1/votar")
    @ResponseBody
    public Response votar(String voto, Integer idPauta, String idUsuario ){
        return facade.votar(voto,idPauta,idUsuario);
    }

    /**
     * Método que inicia o fluxo de obter resultados de uma votação
     * @param idPauta Id da pauta a ser consultada
     * @param idUsuario  ID do usuario que solicitou a consulta
     * @return
     */
    @GetMapping("/v1/buscarResultadosPauta")
    @ResponseBody
    public Response getResultadosVotacao(Integer idPauta, String idUsuario){
        return facade.getResultadosVotacao(idPauta,idUsuario);
    }
}
