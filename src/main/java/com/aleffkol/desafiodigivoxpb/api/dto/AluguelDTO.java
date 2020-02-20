package com.aleffkol.desafiodigivoxpb.api.dto;

import com.aleffkol.desafiodigivoxpb.model.entity.Livro;
import com.aleffkol.desafiodigivoxpb.model.entity.Usuario;
import com.aleffkol.desafiodigivoxpb.model.enums.StatusAluguel;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AluguelDTO {
    private long id;


    private long usuario;


    private Set<Livro> livro;


    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_aluguel;


    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_entrega;

    private String status;
}
