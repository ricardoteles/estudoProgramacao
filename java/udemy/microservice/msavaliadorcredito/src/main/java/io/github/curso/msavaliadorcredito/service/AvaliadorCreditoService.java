package io.github.curso.msavaliadorcredito.service;

import io.github.curso.msavaliadorcredito.clients.CartaoControllerClient;
import io.github.curso.msavaliadorcredito.clients.ClienteControllerClient;
import io.github.curso.msavaliadorcredito.model.CartaoCliente;
import io.github.curso.msavaliadorcredito.model.DadosCliente;
import io.github.curso.msavaliadorcredito.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
    private final ClienteControllerClient clientesClient;
    private final CartaoControllerClient cartoesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }
}
