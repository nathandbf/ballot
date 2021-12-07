package com.sc.ballot.repository;

import com.sc.ballot.entity.Pauta;
import com.sc.ballot.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoJPARepository extends JpaRepository<Voto, Integer> {
    Voto findByCpfAssociadoAndPauta(String cpfAssociado, Pauta pauta);
}
