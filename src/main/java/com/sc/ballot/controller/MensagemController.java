package com.sc.ballot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sc.ballot.entity.KafkaMsg;
import com.sc.ballot.entity.Pauta;
import com.sc.ballot.message.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MensagemController {
    @Autowired
    private KafkaProducer kafkaProducer;

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
            //TODO LOG
            e.printStackTrace();
        }
    }
}
