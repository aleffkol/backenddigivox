package com.aleffkol.desafiodigivoxpb.service;

import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email) throws Exception;
    Optional<Usuario> encontrarPorID(long id) throws Exception;
    List<Usuario> listarUsuario();

}
