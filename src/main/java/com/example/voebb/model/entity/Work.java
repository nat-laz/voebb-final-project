package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "release_year")
    private String releaseYear;

    private String photo;

    @Column(columnDefinition = "text")
    private String description; // summary of the product based on media_type

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();
}

