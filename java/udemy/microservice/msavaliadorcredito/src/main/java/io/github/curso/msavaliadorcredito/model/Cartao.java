package io.github.curso.msavaliadorcredito.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cartao {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
