package io.github.curso.msavaliadorcredito.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartaoAprovado {
    private String cartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}
