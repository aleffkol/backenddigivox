package com.aleffkol.desafiodigivoxpb.service.impl;

import com.aleffkol.desafiodigivoxpb.execption.RegraNegocioException;
import com.aleffkol.desafiodigivoxpb.model.entity.Aluguel;
import com.aleffkol.desafiodigivoxpb.model.enums.StatusAluguel;
import com.aleffkol.desafiodigivoxpb.model.repository.AluguelRepository;
import com.aleffkol.desafiodigivoxpb.service.AluguelService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class AluguelServiceImpl implements AluguelService {


    AluguelRepository aluguelRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    @SneakyThrows

    public void validar(Aluguel aluguel){
        if(aluguel.getUsuario()==null || aluguel.getUsuario().getId()==null){
            throw new RegraNegocioException("Informe um usuário");
        }
        if(aluguel.getLivro()==null){
            throw new RegraNegocioException("Informe pelo menos um livro");
        }
        if(aluguel.getData_entrega()==null){
            throw new RegraNegocioException("È preciso informar a data de entrega");
        }

    }

    @Transactional
    @Override
    public Aluguel salvar(Aluguel aluguel) {
        aluguel.setStatus(StatusAluguel.ALUGADO);
        validar(aluguel);
        return aluguelRepository.save(aluguel);
    }

    @Transactional
    @Override
    public Aluguel atualizar(Aluguel aluguel) {
        Objects.requireNonNull(aluguel.getId());
        validar(aluguel);
        return aluguelRepository.save(aluguel);
    }

    @Override
    public void deletar(Aluguel aluguel) {
        Objects.requireNonNull(aluguel.getId());
        aluguelRepository.delete(aluguel);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Aluguel> buscar(Aluguel aluguelFiltro) {
        Example example = Example.of(aluguelFiltro, ExampleMatcher.matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return aluguelRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Aluguel aluguel, StatusAluguel statusAluguel) {
        aluguel.setStatus(statusAluguel);
        atualizar(aluguel);
    }

    @Override
    public Optional<Aluguel> encontrarPorID(Long id) {
        return aluguelRepository.findById(id);
    }
}
