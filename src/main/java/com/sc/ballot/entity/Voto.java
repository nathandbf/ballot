package com.sc.ballot.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "TB_VOTO")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@SequenceGenerator(name = "seqVote", initialValue = 1, allocationSize = 25)
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqVote")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer idVoto;

    @Column(name = "CPF_ASSOCIADO", nullable = false)
    private String cpfAssociado;

    @ManyToOne
    @JoinColumn(name = "ID_PAUTA", nullable = false)
    private Pauta pauta;

    @Column(name = "VOTO", nullable = false)
    private Integer identificadorVoto;

    public Voto() {

    }

    public Integer getId() {
        return idVoto;
    }

    public void setId(Integer id) {
        this.idVoto = id;
    }

    public Integer getIdentificadorVoto() {
        return identificadorVoto;
    }

    public void setIdentificadorVoto(Integer identificadorVoto) {
        this.identificadorVoto = identificadorVoto;
    }

    public String getCpfAssociado() {
        return cpfAssociado;
    }

    public void setCpfAssociado(String cpfAssociado) {
        this.cpfAssociado = cpfAssociado;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }
}
