package com.sc.ballot.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
 
@Entity
@Table(name = "TB_ASSOCIADO")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Associado {

    @Id
    @Column(name = "CPF",  unique = true, nullable = false)
    private String cpf;
    @Column(name = "NOME", nullable = true)
    private String nome;

    public Associado() {

    }

    public Associado(String cpf) {
        this.cpf = cpf;
    }

    public Associado(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
