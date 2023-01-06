package io.github.curso.msclientes.dto;

import io.github.curso.msclientes.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }
}
