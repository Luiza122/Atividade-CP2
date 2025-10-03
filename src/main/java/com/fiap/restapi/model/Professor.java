package com.fiap.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Professor {
    private Long id;
    private String nome;
    private String email;
    private String departamento;
    private String titulacao;
}