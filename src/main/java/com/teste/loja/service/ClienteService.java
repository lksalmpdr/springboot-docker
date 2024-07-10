package com.teste.loja.service;

import com.teste.loja.exception.EntityAlreadyExistsException;
import com.teste.loja.exception.ResourceNotFoundException;
import com.teste.loja.model.Cliente;
import com.teste.loja.repository.ClienteRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository cli){
        this.clienteRepository = cli;
    }

    public Cliente create(Cliente cliente) throws EntityAlreadyExistsException {
        Set<Cliente> clienteSet = this.findByEmail(cliente.getEmail());

        if(clienteSet.isEmpty()){
            return this.clienteRepository.save(cliente);
        }
        throw new EntityAlreadyExistsException("Cliente com o email" + cliente.getEmail() + " ja existe");
    }

    public Optional<Cliente> findById(Long id) {
        return this.clienteRepository.findById(id);
    }
    public List<Cliente> getAll() {
        return new ArrayList<>(clienteRepository.findAll());

    }

    public Cliente update(Cliente newCustomer, Long id) throws ResourceNotFoundException {
        Optional<Cliente> optionalCliente = this.findById(id);
        Cliente cli = optionalCliente.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        cli.setEmail(newCustomer.getEmail());
        cli.setNome(newCustomer.getNome());
        return cli;
    }

    @Transactional
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Cliente> optionalCliente = this.findById(id);
        Cliente cli = optionalCliente.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        this.clienteRepository.delete(cli);
    }

    public Set<Cliente> findByEmail(String email) {
        return this.clienteRepository.findByEmail(email);
    }
}
