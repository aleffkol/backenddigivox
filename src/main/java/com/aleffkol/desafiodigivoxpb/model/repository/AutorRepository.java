package com.aleffkol.desafiodigivoxpb.model.repository;

import com.aleffkol.desafiodigivoxpb.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
