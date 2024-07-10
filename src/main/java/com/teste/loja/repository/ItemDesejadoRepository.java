package com.teste.loja.repository;

import com.teste.loja.model.ItemDesejado;
import com.teste.loja.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ItemDesejadoRepository extends JpaRepository<ItemDesejado, Long> {

    Optional<ItemDesejado> findById(Long id);

    @Query(value="select i0.id where i0.produtoId = :idProduto and wishlistId = :idWishlist ", nativeQuery = true)
    Set<ItemDesejado> findByProdutoAndWishlist(Long idProduto, Long idWishlist );
}

