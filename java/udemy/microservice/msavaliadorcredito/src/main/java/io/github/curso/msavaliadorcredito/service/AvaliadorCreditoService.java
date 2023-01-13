package io.github.curso.msavaliadorcredito.service;

import feign.FeignException;
import io.github.curso.msavaliadorcredito.clients.CartaoControllerClient;
import io.github.curso.msavaliadorcredito.clients.ClienteControllerClient;
import io.github.curso.msavaliadorcredito.exception.DadosClienteNotFoundException;
import io.github.curso.msavaliadorcredito.exception.ErroComunicacaoMicroserviceException;
import io.github.curso.msavaliadorcredito.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            DadosCliente dadosCliente = dadosClienteResponse.getBody();

            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));

                BigDecimal limiteAprovado = fator.multiply(limiteBasico); // limiteAprovado = limiteBasico*(idadeBD/10)

                return new CartaoAprovado(cartao.getNome(), cartao.getBandeira(), limiteAprovado);
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) { // o endpoint em msclientes pode retornar ok ou notFound, ja mscartoes sempre retorna ok
            int status = e.status();                      // logo soh precisa da excecao para o endpoint do msclientes qdo ocorre notFound

            if (status == HttpStatus.NOT_FOUND.value()) {
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }
}
