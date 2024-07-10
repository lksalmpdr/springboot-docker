package com.teste.loja.service;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.ItemDesejado;
import com.teste.loja.model.Produto;
import com.teste.loja.model.Wishlist;
import com.teste.loja.repository.WishlistRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wlr) {
        this.wishlistRepository = wlr;
    }
    public Wishlist create(Wishlist wishlist) throws EntityAlreadyExistsException {
        if(wishlist.getId() != null){
            this.wishlistRepository.findById(wishlist.getId()).orElseThrow(()-> new EntityExistsException(("Wishlist já existe")));
        }
        return this.wishlistRepository.save(wishlist);
    }

    public Optional<Wishlist> findByCustomerId(Long customerId) {
        return this.wishlistRepository.findByClienteId(customerId);
    }
//
//    public Wishlist addProduct(Set<ItemDesejado> newItem, Long id) throws ResourceNotFoundException {
//        Wishlist wishlist = this.wishlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wishlist não encontrada"));
//        wishlist.getItemDesejados().addAll(newProdutos);
//        return wishlist;
//    }

    public void delete(Long id) {
        Wishlist wishlist = this.wishlistRepository.getReferenceById(id);
        this.wishlistRepository.delete(wishlist);
    }

    public List<Wishlist> findAll() {
        return this.wishlistRepository.findAll();
    }
}