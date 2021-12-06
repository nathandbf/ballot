package com.sc.ballot.repository;

import com.sc.ballot.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaJPARepository extends JpaRepository<Pauta, Integer> {
    List<Pauta> findByNomeAndStatusPauta(String nome, Integer status);
}
