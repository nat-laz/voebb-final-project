package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "creators")
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Column(name = "creator_first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "creator_last_name",  nullable = false, length = 60)
    private String lastName;

}
