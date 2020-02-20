package com.aleffkol.desafiodigivoxpb.service.impl;

import com.aleffkol.desafiodigivoxpb.execption.ErroAutenticacao;
import com.aleffkol.desafiodigivoxpb.execption.RegraNegocioException;
import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import com.aleffkol.desafiodigivoxpb.model.repository.UsuarioRepository;
import com.aleffkol.desafiodigivoxpb.service.UsuarioService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @SneakyThrows
    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if(!u.isPresent()){
            throw new ErroAutenticacao("Não existe um usuário cadastrado com este e-mail.");
        }
        if(!u.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("A senha está incorreta.");
        }
        return u.get();


    }

    @SneakyThrows
    @Transactional
    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) throws Exception {
        boolean existe = usuarioRepository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
        }
    }

    @Override
    public Optional<Usuario> encontrarPorID(long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }
}
