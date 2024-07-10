package com.teste.loja.repository;

import com.teste.loja.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findById(Long id);
    Optional<Wishlist> findByClienteId(Long clienteId);
    Optional<Wishlist> findByClienteEmail(String email);

}
