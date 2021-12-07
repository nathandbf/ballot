package com.sc.ballot.controller;

import com.sc.ballot.constant.Constante;
import com.sc.ballot.constant.ConstanteMsgLog;
import com.sc.ballot.constant.PautaStatus;
import com.sc.ballot.dao.BallotDAO;
import com.sc.ballot.entity.*;
import com.sc.ballot.util.Validador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Controller
public class BallotController {

    @Autowired
    private BallotDAO ballotDAO;

    @Autowired
    private MensagemController mensagemController;

    Logger logger = LoggerFactory.getLogger(BallotController.class);

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
                    logger.info(ConstanteMsgLog.CADASTRO_SUCESSO.formatted(idUsuario,pauta.getId()));
                } else {
                    response.setCode(403);
                    response.setMessage(Constante.ERROR_403 + Constante.ERROR_403_PAUTA);
                    logger.warn(ConstanteMsgLog.CADASTRO_NOME_EXISTENTE.formatted(idUsuario,nome));
                }
            }catch (Exception e){
                response.setCode(500);
                response.setMessage(Constante.ERROR_500);
                String param = "nome: "+ nome;
                logger.error(ConstanteMsgLog.ERRO_LOG.formatted(idUsuario, "cadastrarPauta()", param, e.getMessage()));
            }
        }
        else{
            response.setCode(400);
            response.setMessage(Constante.ERROR_400 + Constante.ERROR_400_COMP_NOME_NULL);
            String param = "nome: "+ nome;
            logger.error(ConstanteMsgLog.ERRO_LOG_WE.formatted(idUsuario, "cadastrarPauta()", param));
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
     * @param tempoSegundos tempo em segundos
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
        GenericResponse response = new GenericResponse();
        try {
            Pauta pauta = buscarExistenciaPauta(idPauta,PautaStatus.NAO_INICIALIZADO.getStatus());
            if (pauta == null) {
                response.setCode(400);
                response.setMessage(Constante.ERROR_400 +  Constante.ERROR_400_COMP_ABERTURA_PAUTA);
                logger.warn(ConstanteMsgLog.ABRIR_PAUTA_ID_INEXISTENTE.formatted(idUsuario,idPauta));
            }
            else {
                pauta.setDuracaoSegundos(tempoSegundos);
                pauta.setStatusPauta(PautaStatus.ANDAMENTO.getStatus());
                ballotDAO.salvarPauta(pauta);
                response.setCode(200);
                response.setMessage(Constante.PAUTA_200_ABERTURA);
                logger.info(ConstanteMsgLog.PAUTA_ABERTA_SUCESSO.formatted(idUsuario,pauta.getId(),tempoSegundos));
            }
        }catch (Exception e){
            response.setCode(500);
            response.setMessage(Constante.ERROR_500);
            String param = "idPauta: "+ idPauta + "tempoSegundos: "+ tempoSegundos;
            logger.error(ConstanteMsgLog.ERRO_LOG.formatted(idUsuario, "abrirPauta()", param, e.getMessage()));
        }
        return response;
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
            logger.info(ConstanteMsgLog.PAUTA_FECHADA_SUCESSO.formatted(pauta.getId()));
            mensagemController.enviarMensagemConclusao(pauta);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            String param = "idPauta: "+ idPauta + "tempoSegundos: "+ tempoSegundos;
            logger.error(ConstanteMsgLog.ERRO_FECHAR_TO.formatted( "fecharPauta()", param, ie.getMessage()));
        }
        catch (Exception e) {
            Thread.currentThread().interrupt();
            String param = "idPauta: "+ idPauta + "tempoSegundos: "+ tempoSegundos;
            logger.error(ConstanteMsgLog.ERRO_FECHAR.formatted("fecharPauta()", param, e.getMessage()));
        }
    }

    /**
     * Método responsavel por realizar o voto.
     * R1 - Verificar a integridade do voto
     * R2 - Verificar se a pauta está com o status ANDAMENTO
     * R3 - Verificar se o usuario já votou nesta pauta
     * R4 - Verificar válidade do ID (CPF) do usuario
     * R5 - Salvar voto;
     * @param voto String "Sim" or "Não"
     * @param idPauta Id da pauta a ser votada
     * @param idUsuario  ID do usuario que solicitou o voto (seu CPF)
     * @return Objeto Response com o código e status da operação
     */
    public Response votar(String voto, Integer idPauta, String idUsuario) {
        GenericResponse response = new GenericResponse();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            Future<Boolean> statusCpfAssociado = executor.submit(() -> validarCpfAssociado(idUsuario));
            if (!verificarIntegridadeVoto(voto)) {
                response.setCode(400);
                response.setMessage(Constante.ERROR_400 + Constante.ERROR_400_COMP_VOTAR);
                logger.warn(ConstanteMsgLog.VOTO_FORMATO_INVALIDO.formatted(idUsuario,voto));
                return response;
            }
            Pauta pautaVotagem = buscarPautaPorId(idPauta);
            response = validarPautaVotacaoResponse(pautaVotagem);
            if (response.getMessage() != null){
                logger.warn(ConstanteMsgLog.VOTO_NAO_ANDAMENTO.formatted(idUsuario,idPauta));
                return response;
            }
            if(verificarVotoRepetido(pautaVotagem,idUsuario)){
                response.setCode(403);
                response.setMessage(Constante.ERROR_403 + Constante.ERROR_403_REPETIDO);
                logger.warn(ConstanteMsgLog.VOTO_REPETIDO.formatted(idUsuario,idPauta));
                return response;
            }

            if(!statusCpfAssociado.get(10,TimeUnit.SECONDS)){
                response.setCode(403);
                response.setMessage(Constante.ERROR_403 + Constante.ERROR_403_VOTO);
                logger.warn(ConstanteMsgLog.CPF_NAO_AUTORIZADO.formatted(idUsuario));
                return response;
            }
            inserirVoto(voto,pautaVotagem,idUsuario);
            response.setCode(200);
            response.setMessage(Constante.PAUTA_200_VOTO);
            logger.info(ConstanteMsgLog.VOTO_SUCESSO.formatted(idUsuario,idPauta,voto));
        }catch (Exception e){
            response.setCode(500);
            response.setMessage(Constante.ERROR_500);
            String param = "voto: "+ voto +"idPauta: "+ idPauta;
            logger.error(ConstanteMsgLog.ERRO_LOG.formatted(idUsuario, "votar()", param, e.getMessage()));
        } finally {
            executor.shutdown();
        }
        return response;
    }

    /**
     * Método auxiliar que realiza a inserção do voto no banco
     * @param voto a string com o voto do usuario
     * @param pautaVotagem a pauta da votacao
     * @param idUsuario cpf do usuario votando
     * @return Voto inserido no banco
     */
    private Voto inserirVoto(String voto, Pauta pautaVotagem, String idUsuario) {
        Voto votoObject = new Voto();
        votoObject.setCpfAssociado(idUsuario);
        votoObject.setPauta(pautaVotagem);
        if(Constante.VOTO_OPCAO1.equalsIgnoreCase(voto)){
            votoObject.setIdentificadorVoto(Constante.VOTO_OPCAO1_INT);
        }else{
            votoObject.setIdentificadorVoto(Constante.VOTO_OPCAO2_INT);
        }
        return ballotDAO.inserirVoto(votoObject);
    }

    /**
     * Método auxiliar que verifica se o voto já foi realizado em uma votação especifica.
     * @param pauta Pauta a ser votada
     * @param cpfAssociado  ID do usuario que solicitou o voto (seu CPF)
     * @return TRUE se o voto já foi feito, FALSE caso o voto ainda não foi realizado
     */
    private boolean verificarVotoRepetido(Pauta pauta, String cpfAssociado) {
        return (ballotDAO.buscarVoto(pauta,cpfAssociado) != null);
    }

    /**
     * Método auxiliar que verifica se a pauta está no status de ANDAMENTO, caso não estiver um objeto response é preparado informando o erro.
     * @param pautaVotagem
     * @return Objeto response com o erro se a pauta não estiver em ANDAMENTO, o mesmo objeto caso contrario.
     */
    private GenericResponse validarPautaVotacaoResponse(Pauta pautaVotagem) {
        GenericResponse response = new GenericResponse();
        if (pautaVotagem == null) {
            response.setCode(400);
            response.setMessage(Constante.ERROR_400 +  Constante.ERROR_400_COMP_PAUTA_NAO_EXISTE);
        } else if (pautaVotagem.getStatusPauta() == PautaStatus.ANDAMENTO.getStatus()) {
            return response;
        }
        else if (pautaVotagem.getStatusPauta() == PautaStatus.NAO_INICIALIZADO.getStatus()) {
            response.setCode(400);
            response.setMessage(Constante.ERROR_400 +  Constante.ERROR_400_COMP_PAUTA_NAO_INICIALIZADA);
        }
        else if (pautaVotagem.getStatusPauta() == PautaStatus.ENCERRADO.getStatus()) {
            response.setCode(400);
            response.setMessage(Constante.ERROR_400 +  Constante.ERROR_400_COMP_PAUTA_ENCERRADA);
        }
        return response;
    }

    /**
     * Método auxiliar responsavel por buscar e verificar a existencia de uma pauta com um status especifico
     * @param idPauta id da pauta
     * @return Objeto Pauta se o Id da pauta estiver registrado, null caso contrario
     */
    private Pauta buscarPautaPorId(Integer idPauta) {
        return ballotDAO.buscarPautaPorId(idPauta);
    }

    /**
     * Método que verifica se o voto é uma das duas opções válidas ignorando maisculas ou minusculas.
     * @param voto
     * @return TRUE se o voto for valido, FALSE caso contrario
     */
    private boolean verificarIntegridadeVoto(String voto) {
        if(Constante.VOTO_OPCAO1.equalsIgnoreCase(voto) ||
                Constante.VOTO_OPCAO2.equalsIgnoreCase(voto) ){
            return true;
        }
        return false;
    }



    /**
     * Valida se o CPF está aprovado para votar baseado em um serviço externo.
     * @param cpf
     * @return TRUE se o cpf for aprovado para votar, FALSE caso não for
     */
    public boolean validarCpfAssociado(String cpf) {
        ExternalCallController externalCallController = new ExternalCallController();
        return externalCallController.validadorCpf(cpf);
    }


    /**
     * Método que realiza a contagem de votos de uma pauta
     * R1 - A pauta não pode ser como NAO_INICIALIZADO
     * @param idPauta
     * @return Um objeto response contendo o resultado da votacao
     */
    public Response getResultadosVotacao(Integer idPauta, String idUsuario) {
        GenericResponse genericResponse = new GenericResponse();
        try{
            Pauta pauta = buscarPautaPorId(idPauta);
            if(PautaStatus.NAO_INICIALIZADO.getStatus()==pauta.getStatusPauta()){
                genericResponse.setCode(400);
                genericResponse.setMessage(Constante.ERROR_400 + Constante.ERROR_400_COMP_PAUTA_NAO_INICIALIZADA);
                return genericResponse;
            }
            int countSim = 0;
            int countNao = 0;
            for (Voto voto:pauta.getListaVotos()) {
                if(Constante.VOTO_OPCAO1_INT == voto.getIdentificadorVoto())
                    countSim++;
                else
                    countNao++;
            }
            VotacaoResult votacaoResult = new VotacaoResult(pauta.getNome(), countSim, countNao, pauta.getDuracaoSegundos(),null);
            genericResponse.setCode(200);
            if(PautaStatus.ANDAMENTO.getStatus()==pauta.getStatusPauta()){
                votacaoResult.setStatus(Constante.STATUS_ANDAMENTO);
                genericResponse.setMessage(Constante.PAUTA_200_BUSCA_NAO_FINALIZADA);
                genericResponse.setResponse(votacaoResult);
            }
            else{
                votacaoResult.setStatus(Constante.STATUS_FINALIZADA);
                genericResponse.setMessage(Constante.PAUTA_200_BUSCA_FINALIZADA);
                genericResponse.setResponse(votacaoResult);
            }
        }catch (Exception e){
            genericResponse.setCode(500);
            genericResponse.setMessage(Constante.ERROR_500);
            e.printStackTrace(); //TODO LOGAR
        }
        return genericResponse;
    }
}
