package com.sc.ballot.persistence;

import com.sc.ballot.dao.BallotDAO;
import com.sc.ballot.entity.Pauta;
import com.sc.ballot.entity.Voto;
import com.sc.ballot.repository.PautaJPARepository;
import com.sc.ballot.repository.VotoJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BallotDAOImpl implements BallotDAO {

    @Autowired
    private PautaJPARepository pautaJPARepository;
    @Autowired
    private VotoJPARepository votoJPARepository;

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

    @Override
    public Pauta buscarPautaPorId(Integer idPauta) {
        return pautaJPARepository.findByIdPauta(idPauta);
    }

    @Override
    public Voto buscarVoto(Pauta pauta, String cpfAssociado) {
        return votoJPARepository.findByCpfAssociadoAndPauta(cpfAssociado, pauta);
    }

    @Override
    public Voto inserirVoto(Voto voto) {
        return votoJPARepository.save(voto);
    }
}
