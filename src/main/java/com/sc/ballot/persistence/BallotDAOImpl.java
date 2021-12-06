package com.sc.ballot.persistence;

import com.sc.ballot.dao.BallotDAO;
import com.sc.ballot.entity.Pauta;
import com.sc.ballot.repository.PautaJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BallotDAOImpl implements BallotDAO {

    @Autowired
    private PautaJPARepository pautaJPARepository;

    @Override
    public Pauta cadastrarPauta(Pauta pauta) {
        return pautaJPARepository.save(pauta);
    }

    @Override
    public List<Pauta> buscarPautasPorNomeStatus(String nome, Integer status) {
        return pautaJPARepository.findByNomeAndStatusPauta(nome,status);
    }

    @Override
    public Pauta buscarPautaPorIdStatus(Integer idPauta, Integer status) {
        return pautaJPARepository.findByIdPautaAndStatusPauta(idPauta,status);
    }

    @Override
    public Pauta salvarPauta(Pauta pauta) {
        return pautaJPARepository.save(pauta);
    }
}
