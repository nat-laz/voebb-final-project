package com.example.voebb.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "country_id")
    private Long id;

    @Column(name = "country_name", nullable = false, length = 80, unique = true)
    private String name;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Product> products;
}