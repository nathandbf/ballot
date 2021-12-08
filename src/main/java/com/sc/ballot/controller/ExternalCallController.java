package com.sc.ballot.controller;

import com.sc.ballot.constant.Constante;
import com.sc.ballot.constant.ConstanteMsgLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExternalCallController {
    Logger logger = LoggerFactory.getLogger(ExternalCallController.class);
    /**
     * Realiza uma chamada externa para verificar se o CPF é valido
     * @param cpf
     * @return TRUE para válido FALSE para invalido.
     */
    public boolean validadorCpf(String cpf){
        String uri = "https://user-info.herokuapp.com/users/"+cpf;
        String result = null;
        RestTemplate restTemplate = new RestTemplate();
        try{
            result = restTemplate.getForObject(uri, String.class);
        }catch (RestClientException e){
            String param = "cpf: "+cpf;
            logger.error(ConstanteMsgLog.ERRO_REST.formatted("validadorCpf() - https://user-info.herokuapp.com/users/ ", param , e.getMessage()));
        }
        if(result == null || result.contains(Constante.UNABLE_TO_VOTE)){
            return false;
        }
        return true;
    }
}
