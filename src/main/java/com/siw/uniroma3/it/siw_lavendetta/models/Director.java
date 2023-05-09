package com.siw.uniroma3.it.siw_lavendetta.models;

import com.siw.uniroma3.it.siw_lavendetta.constants.DefaultSaveLocations;
import com.siw.uniroma3.it.siw_lavendetta.constants.StaticURLs;
import org.apache.tomcat.jni.Local;
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
//    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate birthDate;

    @Column(name = "death_date")
//    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate deathDate;

    @Column(name = "foto",nullable = true)
    private String image;

//    @OneToMany(mappedBy = "director")
//    private Set<Film> films = new HashSet<>();

    // costruttori, getter, setter, equals, hashCode, toString
    public Director(){

    }
    public Director( String firstName, String lastName, LocalDate birthDate, LocalDate deathDate, String image) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.image = image;
//        this.films=new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        if (director.getDeathDate() == null && this.getDeathDate()==null) {
            return Objects.equals(firstName, director.firstName) && Objects.equals(lastName, director.lastName) &&
                    Objects.equals(birthDate, director.birthDate);
        } else if (director.getDeathDate() != null && this.getDeathDate() != null) {
            return Objects.equals(firstName, director.firstName) && Objects.equals(lastName, director.lastName) &&
                    Objects.equals(birthDate, director.birthDate) && Objects.equals(deathDate, director.deathDate);

        }
        return false;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {

        this.birthDate =  birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public Set<Film> getFilms() {
//        return films;
//    }
//
//    public void setFilms(Set<Film> films) {
//        this.films = films;
//    }

    public String getAbsoluteUrl(){ return StaticURLs.DIRECTOR_DETAIL_URL+this.getId();}

    public String getFullName(){
        return this.firstName +" "+ this.lastName;
    }
    @Transient
    public String getImagePath() {
        if (this.image == null || id == null) return null;

        return "/"+ DefaultSaveLocations.DEFAULT_DIRECTORS_IMAGE_SAVE + this.image;
    }

}