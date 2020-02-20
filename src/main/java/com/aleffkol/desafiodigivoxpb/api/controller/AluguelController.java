package com.aleffkol.desafiodigivoxpb.api.controller;

import com.aleffkol.desafiodigivoxpb.api.dto.AluguelDTO;
import com.aleffkol.desafiodigivoxpb.execption.RegraNegocioException;
import com.aleffkol.desafiodigivoxpb.model.entity.Aluguel;
import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import com.aleffkol.desafiodigivoxpb.model.enums.StatusAluguel;
import com.aleffkol.desafiodigivoxpb.service.AluguelService;
import com.aleffkol.desafiodigivoxpb.service.UsuarioService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alugueis")
public class AluguelController {

    @Autowired
    AluguelService  aluguelService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody AluguelDTO aluguelDTO){

        try {
            Aluguel aluguelConvertido = conveterDTO(aluguelDTO);

            aluguelConvertido = aluguelService.salvar(aluguelConvertido);
            return new ResponseEntity(aluguelConvertido, HttpStatus.CREATED);
        }
        catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long idAluguel, @RequestBody AluguelDTO aluguelDTO ){
        return aluguelService.encontrarPorID(idAluguel).map(entity ->{

            try {
                Aluguel aluguelConvertido = conveterDTO(aluguelDTO);
                aluguelConvertido.setId(entity.getId());
                aluguelService.atualizar(aluguelConvertido);
                return ResponseEntity.ok(aluguelConvertido);
            }
            catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( ()-> new ResponseEntity("Aluguel não encontrado", HttpStatus.BAD_REQUEST));

    }
    public ResponseEntity deletar(@PathVariable("id") Long idAluguel){
        return aluguelService.encontrarPorID(idAluguel).map(entity ->{
            aluguelService.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(()->
                new ResponseEntity("Aluguel não encontrado no BD.", HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity buscar(
            @RequestParam(value ="data_entrega" , required =false )LocalDate data_entrega,
            @RequestParam(value ="status", required = false) StatusAluguel status,
            @RequestParam("usuario") Long id_usuario
            ){
            Aluguel aluguelFiltro = new Aluguel();
            aluguelFiltro.setData_entrega(data_entrega);
            aluguelFiltro.setStatus(status);
            Optional<Usuario> usuario = usuarioService.encontrarPorID(id_usuario);
            if(!usuario.isPresent()){
                return ResponseEntity.badRequest().body("Não foi possível encontrar o usuário");
            }else {
                aluguelFiltro.setUsuario(usuario.get());
            }
            List<Aluguel> alugueis = aluguelService.buscar(aluguelFiltro);
            return ResponseEntity.ok(alugueis);

    }
    @SneakyThrows
    private Aluguel conveterDTO(AluguelDTO aluguelDTO){
        Aluguel aluguel = new Aluguel();

        aluguel.setId(aluguelDTO.getId());
        aluguel.setData_entrega(aluguel.getData_entrega());
        aluguel.setData_aluguel(aluguelDTO.getData_aluguel());
        aluguel.setStatus(StatusAluguel.valueOf(aluguelDTO.getStatus()));

        Usuario usuario = usuarioService
                .encontrarPorID(aluguelDTO.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        aluguel.setUsuario(usuario);

        return aluguel;
    }

}
