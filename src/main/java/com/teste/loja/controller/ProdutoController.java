package com.teste.loja.controller;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.Produto;
import com.teste.loja.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
@Tag(name="produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService ps){
        this.produtoService = ps;
    }

    @Operation(summary = "Criar produto", method = "POST"
            , parameters = {@Parameter(name = "newProduto")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Novo produto recebida OK"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida")
    })
    @PostMapping(value = "/novo", name = "novo", produces = {"application/json"})
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Validated Produto newProduto
            , UriComponentsBuilder uriBuilder){
        try{
            Produto product = produtoService.create(newProduto);
            URI uri = uriBuilder.path("/product/{id}").buildAndExpand(newProduto.getId()).toUri();
            return ResponseEntity.created(uri).body(product);
        }catch (EntityAlreadyExistsException e){
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }
    }
    @Operation(summary = "Buscar todos produtos", method = "GET")
    @GetMapping(value = "/todos")
    public List<Produto> getAll(){
        return produtoService.getAll();
    }

    @Operation(summary = "Buscar produto por id", method = "GET"
            , parameters = {@Parameter(name = "id")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos recebida OK"),
            @ApiResponse(responseCode = "422", description = "Requisição inválida")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> read(@PathVariable Long id) {
        Optional<Produto> product = produtoService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Alterar produto", method = "PUT"
            , parameters = {@Parameter(name = "id")})
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Validated Produto newProduct) throws ResourceNotFoundException {
        try{
        Produto product = produtoService.update(newProduct, id);
        return ResponseEntity.ok(product);
        }catch (ResourceNotFoundException resourceNotFoundException){
            return ResponseEntity.ok().body(resourceNotFoundException.getLocalizedMessage());
        }
    }

    @Operation(summary = "Apaga produto", method = "DELETE"
            , parameters = {@Parameter(name = "id")})
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) throws ResourceNotFoundException {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
