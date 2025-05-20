package com.example.voebb.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Primary-key object for the creator_product_relation table
 * (creator_id + product_id + creator_role_id)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CreatorRelationId implements Serializable {
    private Long creatorId;
    private Long productId;
    private Long creatorRoleId;

}