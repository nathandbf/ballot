package com.sc.ballot.controller;

import com.sc.ballot.constant.Constante;
import com.sc.ballot.constant.PautaStatus;
import com.sc.ballot.dao.BallotDAO;
import com.sc.ballot.entity.GenericResponse;
import com.sc.ballot.entity.Pauta;
import com.sc.ballot.entity.Response;
import com.sc.ballot.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BallotController {

    @Autowired
    private BallotDAO ballotDAO;

    /**
     * Método responsavel por cadastrar uma nova pauta na persistencia
     * R1 - Nome nao pode ser nulo ou vazio
     * R2 - O nome da pauta nao pode ser igual ao de outra pauta com os Status de NAO_INICIALIZADO ou ANDAMENTO
     *
     * @param nome Nome da nova Pauta
     * @param idUsuario ID do usuario que solicitou o cadastro
     * @return Objeto Response com o código e status da operação
     */
    public Response cadastrarPauta(String nome, String idUsuario) {
        Validador validador = new Validador();
        GenericResponse response = new GenericResponse();
        if(validador.validarString(nome)){
            try{
                List<Pauta> pautas = buscarPautasPorNomeStatus(nome,
                        PautaStatus.NAO_INICIALIZADO.getStatus(),
                        PautaStatus.ANDAMENTO.getStatus());
                if(pautas == null || pautas.isEmpty()){
                    Pauta pauta = new Pauta();
                    pauta.setStatusPauta(PautaStatus.NAO_INICIALIZADO.getStatus());
                    pauta.setDuracaoSegundos(Constante.TEMPO_INICIAL_PADRAO_PAUTA);
                    pauta.setNome(nome);
                    ballotDAO.cadastrarPauta(pauta);
                    response.setCode(200);
                    response.setMessage(Constante.PAUTA_200_CADASTRO);
                    //TODO LOGAR
                } else {
                    response.setCode(403);
                    response.setMessage(Constante.ERROR_403 + Constante.ERROR_403_PAUTA);
                    //TODO LOGAR
                }
            }catch (Exception e){
                response.setCode(500);
                response.setMessage(Constante.ERROR_500);
                e.printStackTrace(); //TODO LOGAR
            }
        }
        else{
            response.setCode(400);
            response.setMessage(Constante.ERROR_400);
            //TODO LOGAR
        }
        return response;
    }

    /**
     * Método auxiliar responsavel por procurar uma lista de pautas com 1 ou 2 status.
     * @param nome nome das pautas a serem procuradas
     * @param statusPrimario  status da pauta (obrigatorio)
     * @param statusSecundario status da pauta (opcional)
     * @return Lista de pautas correspondentes aos filtros solicitados
     */
    private List<Pauta> buscarPautasPorNomeStatus(String nome,Integer statusPrimario,Integer statusSecundario){
        List<Pauta> listaPautaStatus= new ArrayList<>();
        if(statusPrimario == null){
            return listaPautaStatus;
        }
        else if(statusSecundario==null){
            listaPautaStatus =  ballotDAO.buscarPautasPorNomeStatus(nome,statusPrimario);
        }
        else{
            listaPautaStatus.addAll(ballotDAO.buscarPautasPorNomeStatus(nome,statusPrimario));
            listaPautaStatus.addAll(ballotDAO.buscarPautasPorNomeStatus(nome,statusSecundario));
        }
        return listaPautaStatus;
    }

}
