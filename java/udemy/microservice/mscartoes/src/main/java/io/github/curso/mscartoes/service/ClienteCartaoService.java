package io.github.curso.mscartoes.service;

import io.github.curso.mscartoes.model.ClienteCartao;
import io.github.curso.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listarCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
