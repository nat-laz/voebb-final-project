package com.example.voebb.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreatorProductRelation> creatorProductRelations = new ArrayList<>();

    public void addRelation(CreatorProductRelation creatorProductRelation){
        creatorProductRelations.add(creatorProductRelation);
        creatorProductRelation.setCreator(this);
    }
}
