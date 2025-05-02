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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType type;


    @Column(nullable = false)
    private String title;

    @Column(name = "release_year")
    private String releaseYear;

    private String photo;

    @Column(columnDefinition = "text")
    private String description; // summary of the product based on media_type

    /**
     * Non-NULL for digital products (ebook)
     * NULL for physical ones (book, dvd, board-game)
     */
    @Column(name = "product_link_to_emedia")
    private String productLinkToEmedia;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private BookDetails bookDetails;


    @ManyToMany
    @JoinTable(
            name = "language_relation",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "country_relation",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();
}
