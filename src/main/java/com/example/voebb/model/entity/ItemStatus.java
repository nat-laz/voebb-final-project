package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_status")
public class ItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_status_id")
    private Long id;

    @Column(name = "item_status_name", nullable = false, unique = true)
    private String name; // ['borrowed, reserved, available, lost, damaged' ]
}
