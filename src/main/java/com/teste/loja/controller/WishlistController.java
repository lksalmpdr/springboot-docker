package com.teste.loja.controller;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.Produto;
import com.teste.loja.model.Wishlist;
import com.teste.loja.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wl){
        this.wishlistService = wl;
    }

    @Operation(summary = "Criar wishlist", method = "POST"
            , parameters = {@Parameter(name = "newWishlist")})
    @PostMapping(value = "/novo", name = "novo", produces = {"application/json"})
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Validated Wishlist newWishlist
            , UriComponentsBuilder uriBuilder) {
        Wishlist wishlist = null;
        System.out.println(newWishlist.getCliente());
        try {
            wishlist = wishlistService.create(newWishlist);
            URI uri = uriBuilder.path("/wishlist/{id}").buildAndExpand(newWishlist.getId()).toUri();
            return ResponseEntity.created(uri).body(wishlist);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
        }
    }

    @Operation(summary = "Buscar wishlist por id", method = "GET"
            , parameters = {@Parameter(name = "customerId")})
    @GetMapping(value="/{customerId}", name = "buscar", produces = {"application/json"})
    public ResponseEntity<Wishlist> read(@PathVariable Long customerId) {
        Optional<Wishlist> wishlist = wishlistService.findByCustomerId(customerId);
        return wishlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar todas wishlist", method = "GET")
    @GetMapping(value="/todos", name = "todos", produces = {"application/json"})
    public ResponseEntity<List<Wishlist>> getAll() {
        List<Wishlist> wishlistSet = this.wishlistService.findAll();
        return ResponseEntity.ok().body(wishlistSet);
    }


//    @Operation(summary = "Alterar wishlist por id", method = "PUT"
//            , parameters = {@Parameter(name = "id")})
//    @PutMapping(value="/{id}", name="alterar", produces = "plain/text")
//    @Transactional
//    public ResponseEntity<?> update(@PathVariable Long id
//            , @RequestBody @Validated Set<Produto> newProducts) {
//        try{
//        Wishlist wishlist = wishlistService.addProduct(newProducts, id);
//        return ResponseEntity.ok(wishlist);
//        }catch (ResourceNotFoundException resourceNotFoundException){
//            return ResponseEntity.unprocessableEntity().body(resourceNotFoundException.getLocalizedMessage());
//        }
//    }

    @Operation(summary = "Apaga wishlist por id", method = "DELETE"
            , parameters = {@Parameter(name = "id")})
    @DeleteMapping(value="/{id}", name="apaga", produces = "text/plain")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
