package com.siw.uniroma3.it.siw_lavendetta.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "death_date", nullable = true)
    private LocalDate deathDate;

    @Column(name = "image",nullable = true)
    private String image;

    @ManyToMany(mappedBy = "actors")
    private Set<Film> films = new HashSet<>();

    // costruttori, getter, setter, equals, hashCode, toString
}
