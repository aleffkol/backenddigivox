package com.aleffkol.desafiodigivoxpb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "livro", schema = "desafio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "isbn")
    private long isbn;

    @Column(name = "descricao")
    private String descricao;

    @ManyToMany
    @JoinColumn(name = "autor")
    private Set<Autor> autor;

    @Column(name = "valor")
    private BigDecimal valor;
}
