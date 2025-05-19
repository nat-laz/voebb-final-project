package com.example.voebb.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creator_roles")
public class CreatorRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_role_id")
    private Long id;

    @Column(name = "creator_role", nullable = false, unique = true, length = 30)
    private String creatorRoleName;          // [AUTHOR, DIRECTOR, CO_AUTHOR etc. ]
}
