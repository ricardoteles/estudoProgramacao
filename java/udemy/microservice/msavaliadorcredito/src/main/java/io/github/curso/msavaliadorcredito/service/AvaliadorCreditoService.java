package io.github.curso.msavaliadorcredito.service;

import feign.FeignException;
import io.github.curso.msavaliadorcredito.clients.CartaoControllerClient;
import io.github.curso.msavaliadorcredito.clients.ClienteControllerClient;
import io.github.curso.msavaliadorcredito.exception.DadosClienteNotFoundException;
import io.github.curso.msavaliadorcredito.exception.ErroComunicacaoMicroserviceException;
import io.github.curso.msavaliadorcredito.model.CartaoCliente;
import io.github.curso.msavaliadorcredito.model.DadosCliente;
import io.github.curso.msavaliadorcredito.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
    private final ClienteControllerClient clientesClient;
    private final CartaoControllerClient cartoesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) { // o endpoint em msclientes pode retornar ok ou notFound, ja mscartoes sempre retorna ok
            int status = e.status();                      // logo soh precisa da excecao para o endpoint do msclientes qdo ocorre notFound

            if (status == HttpStatus.NOT_FOUND.value()) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
}
