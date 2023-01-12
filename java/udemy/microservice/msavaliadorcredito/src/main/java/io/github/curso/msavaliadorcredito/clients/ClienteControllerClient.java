package io.github.curso.msavaliadorcredito.clients;

import io.github.curso.msavaliadorcredito.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(url = "http://localhost:8081", path = "/clientes") // isso eh um exemplo para qdo quiser acessar alguma url, mas
@FeignClient(value = "msclientes", path = "/clientes")            // como temos o balanceamento de carga passamos o microservice no value
public interface ClienteControllerClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);
}
