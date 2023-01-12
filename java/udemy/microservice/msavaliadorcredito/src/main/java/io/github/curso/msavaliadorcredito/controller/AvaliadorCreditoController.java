package io.github.curso.msavaliadorcredito.controller;

import io.github.curso.msavaliadorcredito.model.SituacaoCliente;
import io.github.curso.msavaliadorcredito.service.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {
    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value="situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);
    }

    @PostMapping
    public ResponseEntity avaliacaoCliente(@RequestBody String cpf){
        return ResponseEntity.ok("");
    }
}
