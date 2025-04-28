package com.example.voebb.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //TODO: TBD => migrate to UUID type
    @Column(name = "client_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String password; // TODO: encrypted

    /**
     * Boolean wrapper lets Hibernate store null
     */
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true; // [active, inactive - related to membership]

    @Column(name = "borrowed_books_count", nullable = false)
    @Max(5)
    private Integer borrowedBooksCount = 0;

    // TODO: TBD add more fields => created_at(register_date), updated_at, expired_membership_date

    @ManyToMany
    @JoinTable(
            name = "client_roles_relationship",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "client_role_id")
    )
    private Set<ClientRole> clientRoles = new HashSet<>();

}
