package com.teste.loja.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class ItemDesejado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="produtoId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Produto produto;

    @JoinColumn(name="wishlistId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Wishlist wishlist;
}
