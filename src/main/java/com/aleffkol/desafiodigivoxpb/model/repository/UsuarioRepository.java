package com.aleffkol.desafiodigivoxpb.model.repository;

import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Query Methods
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
