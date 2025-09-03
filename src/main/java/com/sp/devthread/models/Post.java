package com.sp.devthread.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String postTitle;
    private String postImage;
    private String postBody;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    /**
     * A Post should always belongs to a specific User. And a User can make multiple Posts. Therfore we have a Many to One Relationship
     * between Post and User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * A single Post can have multiple Comments, added by different or the same User.Therefore we have a One to Many Relationship
     * between Post and Comment, where One Post have Many Comments.
     */
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<Comment> commentList = new ArrayList<>();

    /**
     * A single Post can have multiple Categories and a single Category could have multiple Post. Therfore we have a Many to Many Relationship
     * between Post and Category, where the Post is the Parent side of the Relationship.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categorySet = new HashSet<>();
}
