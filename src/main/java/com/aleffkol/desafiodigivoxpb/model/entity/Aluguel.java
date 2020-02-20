package com.aleffkol.desafiodigivoxpb.model.entity;



import com.aleffkol.desafiodigivoxpb.model.enums.StatusAluguel;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "aluguel", schema = "desafio")
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluguel {
    //id, status, id usu, id livro, data aluguel, data entrega
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToMany
    @JoinColumn(name = "id_livro")
    private Set<Livro> livro;

    @Column(name = "data_aluguel")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_aluguel;

    @Column(name = "data_entrega")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate data_entrega;

    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private StatusAluguel status;

}
