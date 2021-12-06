package com.sc.ballot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_PAUTA")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@SequenceGenerator(name="seqPauta", initialValue=1, allocationSize=25)
public class Pauta {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqPauta")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer idPauta;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "pauta",fetch = FetchType.LAZY)
    private Set<Voto> listaVotos = new HashSet<>();

    @Column(name = "STATUS", nullable = false)
    private Integer statusPauta;

    @Column(name = "DURACAO_SEGUNDOS", nullable = false)
    private Integer duracaoSegundos;

    public Integer getId() {
        return idPauta;
    }

    public void setId(Integer id) {
        this.idPauta = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Voto> getListaVotos() {
        return listaVotos;
    }

    public void setListaVotos(Set<Voto> listaVotos) {
        this.listaVotos = listaVotos;
    }

    public Integer getStatusPauta() {
        return statusPauta;
    }

    public void setStatusPauta(Integer statusPauta) {
        this.statusPauta = statusPauta;
    }

    public Integer getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(Integer duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }
}
