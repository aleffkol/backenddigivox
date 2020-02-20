package com.aleffkol.desafiodigivoxpb.model.repository;

import com.aleffkol.desafiodigivoxpb.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
