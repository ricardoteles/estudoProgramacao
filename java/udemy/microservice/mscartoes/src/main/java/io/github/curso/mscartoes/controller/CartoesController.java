package io.github.curso.mscartoes.controller;

import io.github.curso.mscartoes.dto.CartaoSaveRequest;
import io.github.curso.mscartoes.dto.CartoesPorClienteResponse;
import io.github.curso.mscartoes.model.Cartao;
import io.github.curso.mscartoes.model.ClienteCartao;
import io.github.curso.mscartoes.service.CartaoService;
import io.github.curso.mscartoes.service.ClienteCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {
    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        cartaoService.save(request.toModel());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda) {
        List<Cartao> lista = cartaoService.getCartoesRendaMenorIgual(renda);

        return ResponseEntity.ok(lista);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> lista = clienteCartaoService.listarCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }
}
