package io.github.curso.msavaliadorcredito.clients;

import io.github.curso.msavaliadorcredito.model.Cartao;
import io.github.curso.msavaliadorcredito.model.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="mscartoes", path="/cartoes")
public interface CartaoControllerClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);
}
