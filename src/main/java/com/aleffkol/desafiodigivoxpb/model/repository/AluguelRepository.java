package com.aleffkol.desafiodigivoxpb.model.repository;

import com.aleffkol.desafiodigivoxpb.model.entity.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
}
