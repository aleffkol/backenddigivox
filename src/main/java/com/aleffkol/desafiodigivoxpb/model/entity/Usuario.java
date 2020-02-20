package com.aleffkol.desafiodigivoxpb.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="usuario", schema = "desafio")
//Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

//     public static void main(String[] args){
//         Usuario usuario = new Usuario();
//         usuario.setEmail("email");
//         usuario.setNome("nome");
//         usuario.setSenha("senha");
//     }
}
