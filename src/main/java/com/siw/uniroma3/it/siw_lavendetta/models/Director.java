package com.siw.uniroma3.it.siw_lavendetta.models;

import com.siw.uniroma3.it.siw_lavendetta.constants.StaticURLs;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "director")
public class Director {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private String birthDate;

    @Column(name = "death_date", nullable = true)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private String deathDate;

    @Column(name = "foto",nullable = true)
    private String image;

    @OneToMany(mappedBy = "director")
    private Set<Film> films = new HashSet<>();

    // costruttori, getter, setter, equals, hashCode, toString
    public Director(){

    }
    public Director( String firstName, String lastName, String birthDate, String deathDate, String image) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.image = image;
        this.films=new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        return Objects.equals(id, director.id) && Objects.equals(firstName, director.firstName) && Objects.equals(lastName, director.lastName) && Objects.equals(birthDate, director.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {

        this.birthDate =  birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public String getAbsoluteUrl(){ return StaticURLs.DIRECTOR_DETAIL_URL+this.getId();}

    public String getFullName(){
        return this.firstName +" "+ this.lastName;
    }

}