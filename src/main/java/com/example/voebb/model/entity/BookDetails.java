package com.example.voebb.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_details")
public class BookDetails {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "book_isbn", nullable = false)
    private String isbn;

    @Column(name = "book_edition")
    private String edition;

    @Min(1)
    @Column(name = "book_pages", nullable = false)
    private Integer pages;
}
