package com.sc.ballot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.ballot.constant.ConstanteMsgLog;
import com.sc.ballot.entity.KafkaMsg;
import com.sc.ballot.entity.Pauta;
import com.sc.ballot.message.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MensagemController {
    @Autowired
    private KafkaProducer kafkaProducer;
    Logger logger = LoggerFactory.getLogger(MensagemController.class);

    public void enviarMensagemConclusao(Pauta pauta){
        KafkaMsg kafkaMsg = new KafkaMsg();
        pauta.setListaVotos(null);
        kafkaMsg.setValor(pauta);
        kafkaMsg.setMensagem("A pauta: "+ pauta.getNome()+" de ID: "+ pauta.getId()+" foi finalizada.");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String mensagem = objectMapper.writeValueAsString(kafkaMsg);
            kafkaProducer.sendMessage(mensagem);
        } catch (JsonProcessingException e) {
            logger.error(ConstanteMsgLog.ERRO_LOG_JSON.formatted("cadastrarPauta()", pauta.getId(), e.getMessage()));
            e.printStackTrace();
        }
    }
}
