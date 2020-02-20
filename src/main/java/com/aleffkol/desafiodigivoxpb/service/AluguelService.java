package com.aleffkol.desafiodigivoxpb.service;

import com.aleffkol.desafiodigivoxpb.model.entity.Aluguel;
import com.aleffkol.desafiodigivoxpb.model.enums.StatusAluguel;

import java.util.List;
import java.util.Optional;

public interface AluguelService {
    Aluguel salvar(Aluguel aluguel);

    Aluguel atualizar(Aluguel aluguel);

    void deletar(Aluguel aluguel);
    void validar(Aluguel aluguel);

    List<Aluguel> buscar(Aluguel aluguelFiltro);

    void atualizarStatus(Aluguel aluguel, StatusAluguel statusAluguel);

    Optional<Aluguel> encontrarPorID(Long id);



}
