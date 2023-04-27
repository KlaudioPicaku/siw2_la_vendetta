package com.siw.uniroma3.it.siw_lavendetta.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 20)
    private String title;
    @Column(name = "rating", nullable = false, length = 5)
    private Integer rating;
    @Column(name = "body", nullable = false, length = 500)
    private String body;
    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;
    @Column(name = "author", nullable = false)
    private User author;
    @Column(name = "film", nullable = false)
    private Film film;

    // costruttori, getter, setter, equals, hashCode, toString
}
