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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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


    /**
     * Método responsavel por verificar se o tempo de abertura da votação é válido, caso não seta o TEMPO_PADRAO_PAUTA
     * @param tempoSegundos
     * @return TempoEmSegundos Validado
     */
    public Integer verificarAlterarTempoSegundos(Integer tempoSegundos) {
        if(tempoSegundos == null || tempoSegundos == 0){
            tempoSegundos = Constante.TEMPO_PADRAO_PAUTA;
        }
        return tempoSegundos;
    }


    /**
     * Método responsavel por alterar o status da pauta para ANDAMENTO
     * Setar o tempo de duração da votação e salvar essas mudanças na persistencia
     * R1 - A pauta solicitada deve existir e possuir o status de NAO_INICIALIZADO
     * @param idPauta id da pauta a ser iniciada
     * @param tempoSegundos tempo que a pauta deve ficar aberta em segundos
     * @param idUsuario ID do usuario que solicitou a abertura (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    public GenericResponse abrirPauta(Integer idPauta, Integer tempoSegundos, String idUsuario) {
        GenericResponse genericResponse = new GenericResponse();
        try {
            Pauta pauta = buscarExistenciaPauta(idPauta,PautaStatus.NAO_INICIALIZADO.getStatus());
            if (pauta == null) {
                genericResponse.setCode(400);
                genericResponse.setMessage(Constante.ERROR_400 +  Constante.ERROR_400_COMP_ABERTURA_PAUTA);
                //TODO LOG
            }
            else {
                pauta.setDuracaoSegundos(tempoSegundos);
                pauta.setStatusPauta(PautaStatus.ANDAMENTO.getStatus());
                ballotDAO.salvarPauta(pauta);
                genericResponse.setCode(200);
                genericResponse.setMessage(Constante.PAUTA_200_ABERTURA);
                //TODO LOG
            }
        }catch (Exception e){
            genericResponse.setCode(500);
            genericResponse.setMessage(Constante.ERROR_500);
            e.printStackTrace(); //TODO LOGAR
        }
        return genericResponse;
    }

    /**
     * Método auxiliar responsavel por buscar e verificar a existencia de uma pauta com um status especifico
     * @param idPauta id da pauta
     * @param status status da pauta (obrigatorio)
     * @return Objeto Pauta se o Id da pauta estiver no status solicitado, null caso contrario
     */
    private Pauta buscarExistenciaPauta(Integer idPauta,Integer status){
        if(idPauta == null || idPauta == 0){
            return null;
        }
        return ballotDAO.buscarPautaPorIdStatus(idPauta,status);
    }


    /**
     * Método responsavvel por disparar a thread para o fechamento da pauta depois do tempo determinado
     * @param idPauta Id da Pauta a ser fechada
     * @param tempoSegundos Tempo que a pauta deve permanecer aberta
     */
    public void prepararFechamentoPauta(Integer idPauta, Integer tempoSegundos) {
        ExecutorService executor = Executors.newFixedThreadPool(Constante.MAX_THREAD);
        executor.submit(() -> fecharPauta(idPauta,tempoSegundos));
    }

    /**
     * Thread que espera o tempo de fechamento da pauta e realiza a mudança de status para ENCERRADO
     * @param idPauta Id da Pauta a ser fechada
     * @param tempoSegundos Tempo que a pauta deve permanecer aberta
     */
    private void fecharPauta(Integer idPauta, Integer tempoSegundos){
        try {
            TimeUnit.SECONDS.sleep(tempoSegundos);
            Pauta pauta = buscarExistenciaPauta(idPauta,PautaStatus.ANDAMENTO.getStatus());
            pauta.setStatusPauta(PautaStatus.ENCERRADO.getStatus());
            ballotDAO.salvarPauta(pauta);
            //TODO LOG
            //TODO MSG FINALIZACAO
            System.out.println("Pauta Id:" + pauta.getId() + " finalizada");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            //TODO LOG
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            //TODO LOG
        }
    }

}
