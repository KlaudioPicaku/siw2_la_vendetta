package com.siw.uniroma3.it.siw_lavendetta.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @Column(nullable = true)
    private String image;

    @OneToMany(mappedBy = "film")
    private Set<Review> reviews = new HashSet<>();

    // costruttori, getter, setter, equals, hashCode, toString

}
