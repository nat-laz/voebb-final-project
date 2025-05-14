package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_items")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    /* link with the Product */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /* current circulation item status */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    public ItemStatus status;


    /* bidirectional one-to-one relationship with ItemLocation*/
    @OneToOne(mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private ItemLocation location;


    /**
     * Links item with its location, ensuring bidirectional sync.
     * Required for @MapsId.
     */
    public void addLocation(ItemLocation location) {
        this.location = location;
        location.setItem(this);
    }
}
