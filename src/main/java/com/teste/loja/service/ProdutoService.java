package com.teste.loja.service;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.model.Produto;
import com.teste.loja.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import com.teste.loja.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository pr) {
        this.produtoRepository = pr;
    }

    public Produto create(Produto product) throws EntityAlreadyExistsException {
        Set<Produto> produtoSet = this.produtoRepository.findByNomeAndMarca(product.getMarca(), product.getNome() );

        if(!produtoSet.isEmpty()){
            throw new EntityAlreadyExistsException("Produto " + product.getNome() + " " + product.getMarca() + " já existe");
        }
        return this.produtoRepository.save(product);
    }

    public Optional<Produto> findById(Long id) {
        return this.produtoRepository.findById(id);
    }

    public List<Produto> getAll() {
        return new ArrayList<>(produtoRepository.findAll());

    }

    public Produto update(Produto newProduto, Long id) throws ResourceNotFoundException {
        Optional<Produto> optionalProduto = this.findById(id);
        Produto produto = optionalProduto.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        produto.setNome(newProduto.getNome());
        produto.setPreco(newProduto.getPreco());
        return produto;
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Produto> optionalProduto = this.findById(id);
        Produto produto = optionalProduto.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        this.produtoRepository.delete(produto);
    }

}