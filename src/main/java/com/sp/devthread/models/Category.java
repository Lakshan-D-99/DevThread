package com.sp.devthread.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @ManyToMany(mappedBy = "categorySet")
    private Set<Post> postSet = new HashSet<>();
}
