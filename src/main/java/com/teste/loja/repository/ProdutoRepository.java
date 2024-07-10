package com.teste.loja.repository;

import com.teste.loja.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findById(Long id);

    @Query(value = "select id, preco, marca, nome from produto where marca = :marca and nome = :nome", nativeQuery = true)
    Set<Produto> findByNomeAndMarca(String marca, String nome);
}
