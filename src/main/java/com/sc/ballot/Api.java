package com.sc.ballot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Api {
    /**
     * Teste Simples verificando se o sistema está online.
     * @return
     */
    @GetMapping("/v1/isOnline")
    public String isOnline() {
        return "Sim";
    }
}
