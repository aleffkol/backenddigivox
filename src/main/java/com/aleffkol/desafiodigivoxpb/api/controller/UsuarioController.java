package com.aleffkol.desafiodigivoxpb.api.controller;

import com.aleffkol.desafiodigivoxpb.api.dto.UsuarioDTO;
import com.aleffkol.desafiodigivoxpb.execption.ErroAutenticacao;
import com.aleffkol.desafiodigivoxpb.execption.RegraNegocioException;
import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import com.aleffkol.desafiodigivoxpb.model.repository.UsuarioRepository;
import com.aleffkol.desafiodigivoxpb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .senha(usuarioDTO.getSenha())
                .email(usuarioDTO.getEmail())
                .build();
        try {
           Usuario usuarioSalvo =  usuarioService.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        }
        catch (RegraNegocioException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);

        }
        catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping
    public ResponseEntity listar(){
        List<Usuario> usuarios = usuarioService.listarUsuario();
        return ResponseEntity.ok(usuarios);
    }
}
