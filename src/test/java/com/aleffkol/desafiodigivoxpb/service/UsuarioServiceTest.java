package com.aleffkol.desafiodigivoxpb.service;

import com.aleffkol.desafiodigivoxpb.execption.ErroAutenticacao;
import com.aleffkol.desafiodigivoxpb.execption.RegraNegocioException;
import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import com.aleffkol.desafiodigivoxpb.model.repository.UsuarioRepository;
import com.aleffkol.desafiodigivoxpb.service.impl.UsuarioServiceImpl;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @SpyBean
    UsuarioService usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;


    @SneakyThrows
    @Test
    public void deveValidarEmail() {
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        usuarioService.validarEmail("email@email.com");
    }

    @SneakyThrows
    @Test(expected = RegraNegocioException.class)
    public void deverLancarErroQuandoHouverEmailEmUso(){
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        usuarioService.validarEmail("email@email.com");

    }

    @SneakyThrows
    @Test
    public void deveAutenticarUmUsuario(){
        String email = "email@email.com";
        String senha = "senha";
        Usuario usuario = Usuario.builder().nome("a").senha(senha).email(email).id(1l).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        Usuario result = usuarioService.autenticar(email, senha);

        Assertions.assertThat(result).isNotNull();
    }
    @SneakyThrows
    @Test
    public void deveLancarErroAoPassarEmailNaoEncontrado(){
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Throwable throwable = Assertions.catchThrowable(() ->
        usuarioService.autenticar("email@email.com", "senha"));
        Assertions.assertThat(throwable).isInstanceOf(ErroAutenticacao.class).hasMessage("Não existe um usuário cadastrado com este e-mail.");
    }

    @SneakyThrows
    @Test(expected = ErroAutenticacao.class)
    public void deveLancarErroQuandoASenhaForIncorreta(){
        String senha = "senha";
        Usuario u = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(u));
        usuarioService.autenticar("email@email.com", "123");
    }

    @SneakyThrows
    @Test(expected = RegraNegocioException.class)
    public void naoDeveSalvarUmUsuarioComMesmoEmail(){
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);

        usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
    }


}
