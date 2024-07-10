package com.teste.loja.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Wishlist {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="clienteId")
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="itemDesejadoId")
    Set<ItemDesejado> itemDesejados = new LinkedHashSet<ItemDesejado>();

}