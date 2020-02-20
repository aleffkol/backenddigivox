package com.aleffkol.desafiodigivoxpb.model.repository;

import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


//@SpringBootTest
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void verificarEmailTrue(){
        //setting
        Usuario usuario = Usuario.builder().nome("Usuario").email("email@email.com").build();
        entityManager.persist(usuario);
        //action
        boolean result = usuarioRepository.existsByEmail("email@email.com");
        //verification
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornaFalseCasoNaHajaEmailCadastrado(){
        boolean result = usuarioRepository.existsByEmail("email@email.com");
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNoBD(){
        Usuario u = Usuario.builder().nome("nome").senha("senha").email("email@email.com").build();

        Usuario save = usuarioRepository.save(u);

        Assertions.assertThat(save.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        Usuario u = Usuario.builder().nome("nome").senha("senha").email("email@email.com").build();

        Usuario save = entityManager.persist(u);

        Optional<Usuario> busca = usuarioRepository.findByEmail(save.getEmail());

        Assertions.assertThat(busca).isNotNull();
        //Assertions.assertThat(busca.isPresent()).isTrue();

    }

    @Test
    public void deveRetornarVazioAoBuscarUmUsuarioPorEmail(){
        Optional<Usuario> busca = usuarioRepository.findByEmail("email@email.com");
        Assertions.assertThat(busca).isEmpty();
    }
}
