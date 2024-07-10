package com.teste.loja.controller;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.Cliente;
import com.teste.loja.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/cliente")
@Tag(name="cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService cs ) {
        this.clienteService = cs;
    }

    @Operation(summary = "Inclui novo cliente", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Novo cliente recebida OK"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida")
    })
    @PostMapping(value = "/novo", name = "novo", produces = {"application/json"})
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Validated Cliente newCliente
            , UriComponentsBuilder uriBuilder) {

        try{
            Cliente cli = clienteService.create(newCliente);
            URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(newCliente.getId()).toUri();
            return ResponseEntity.created(uri).body(cli);
        }catch (EntityAlreadyExistsException e){
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }

    }

    @Operation(summary = "Lista todos os clientes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes recebida OK"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida")
    })
    @GetMapping(value="/todos")
    public List<Cliente> getAll (){
        return clienteService.getAll();
    }

    @Operation(summary = "Buscar cliente por id", method = "GET"
            , parameters = {@Parameter(name = "id")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes gerada OK"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> read(@PathVariable Long id) {
        Optional<Cliente> cli = clienteService.findById(id);
        return cli.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar cliente por email", method = "GET"
            , parameters = {@Parameter(name = "email")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes gerada OK"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping("/busca/")
    public ResponseEntity<?> readEmail(@RequestBody String email) {
        Set<Cliente> cli = clienteService.findByEmail(email.trim());
        if(!cli.isEmpty()){
            return ResponseEntity.ok().body(cli);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Alteracliente por id", method = "PUT"
            , parameters = {@Parameter(name = "id")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização de clientes OK"),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id
            , @RequestBody @Validated Cliente newCliente) throws ResourceNotFoundException {
        try {
            Cliente cli = clienteService.update(newCliente, id);
            return ResponseEntity.ok(cli);
        }catch (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.ok().body(resourceNotFoundException.getLocalizedMessage());
        }
    }

    @Operation(summary = "Apaga cliente por id", method = "DELETE"
            , parameters = {@Parameter(name = "id")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes apagado OK"),
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

