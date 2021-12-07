package com.sc.ballot.controller;

import com.sc.ballot.constant.Constante;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExternalCallController {

    /**
     * Realiza uma chamada externa para verificar se o CPF é valido
     * @param cpf
     * @return TRUE para válido FALSE para invalido.
     */
    public boolean validadorCpf(String cpf){
        String uri = "https://user-info.herokuapp.com/users/"+cpf;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        if(result == null || result.contains(Constante.UNABLE_TO_VOTE)){
            return false;
        }
        return true;
    }
}
