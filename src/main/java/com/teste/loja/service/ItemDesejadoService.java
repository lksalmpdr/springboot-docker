package com.teste.loja.service;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.ItemDesejado;
import com.teste.loja.repository.ItemDesejadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemDesejadoService {
    private final ItemDesejadoRepository itemDesejadoRepository;

    public ItemDesejadoService(ItemDesejadoRepository idr) {
        this.itemDesejadoRepository = idr;
    }

    public ItemDesejado create(ItemDesejado item) throws EntityAlreadyExistsException {
        Set<ItemDesejado> itemDesejadoSet = this.itemDesejadoRepository.findByProdutoAndWishlist(item.getProduto().getId(), item.getWishlist().getId() );

        if(!itemDesejadoSet.isEmpty()){
            throw new EntityAlreadyExistsException("Item já cadastrado");
        }
        return this.itemDesejadoRepository.save(item);
    }

    public Optional<ItemDesejado> findById(Long id) {
        return this.itemDesejadoRepository.findById(id);
    }

    public ArrayList<ItemDesejado> getAll() {
        return new ArrayList<ItemDesejado>(itemDesejadoRepository.findAll());

    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<ItemDesejado> optionalItem = this.findById(id);
        ItemDesejado item = optionalItem.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
        this.itemDesejadoRepository.delete(item);
    }

}