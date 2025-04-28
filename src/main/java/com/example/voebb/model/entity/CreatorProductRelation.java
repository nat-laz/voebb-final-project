package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "creator_product_relation")
public class CreatorProductRelation {

    @EmbeddedId
    private CreatorRelationId id = new CreatorRelationId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("creatorId")
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("creatorRoleId")
    @JoinColumn(name = "creator_role_id")
    private CreatorRole creatorRole;
}
